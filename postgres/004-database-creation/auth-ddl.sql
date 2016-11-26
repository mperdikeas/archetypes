DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;

SET CLIENT_ENCODING TO 'latin1' ;

SET statement_timeout = 0;
-- SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;
SET search_path = doesnotexist;

CREATE DOMAIN auth.NameT          AS VARCHAR(50);
CREATE DOMAIN auth.DescrT         AS VARCHAR(200);
CREATE DOMAIN auth.EmailT         AS VARCHAR(200);
CREATE DOMAIN auth.PasswdT        AS VARCHAR(4096);
CREATE DOMAIN auth.SessionIDT     AS VARCHAR(256);
CREATE DOMAIN auth.SSET           AS INTEGER CHECK (value >= 0); -- Seconds Since the Epoch


DROP TABLE   IF     EXISTS auth.authuser;
CREATE TABLE IF NOT EXISTS auth.authuser (
  i                   SERIAL         NOT NULL,
  username            auth.NameT     NOT NULL,
  email               auth.EmailT    NOT NULL,
  fname               auth.NameT     NOT NULL,
  lname               auth.NameT     NOT NULL,  
  hashAlgo            auth.NameT     NOT NULL,
  salt                VARCHAR(32)    NOT NULL,
  password            VARCHAR(4096)  NOT NULL
);
ALTER TABLE auth.authuser           ADD PRIMARY KEY (i);
ALTER TABLE auth.authuser           ADD CONSTRAINT authuser_username_unq UNIQUE(username);
ALTER TABLE auth.authuser           ADD CONSTRAINT authuser_email_unq UNIQUE(email);

DROP   TABLE IF     EXISTS auth.ApplicationPartition;
CREATE TABLE IF NOT EXISTS auth.ApplicationPartition (
  i                   SERIAL         NOT NULL,
  parName             auth.NameT     NOT NULL,
  parDescr            auth.DescrT    NOT NULL
);
ALTER TABLE auth.ApplicationPartition ADD PRIMARY KEY (i);
ALTER TABLE auth.ApplicationPartition ADD CONSTRAINT authuser_ApplicationPartition_unq UNIQUE(parName);


DROP   TABLE IF     EXISTS auth.application;
CREATE TABLE IF NOT EXISTS auth.application (
  i                   SERIAL        NOT NULL,
  appName             auth.NameT    NOT NULL,
  parName             auth.NameT    NOT NULL
);
ALTER TABLE auth.application        ADD PRIMARY KEY (i);
ALTER TABLE auth.application        ADD CONSTRAINT application_appname_unq UNIQUE(appName);
ALTER TABLE auth.application        ADD CONSTRAINT application_appname_partition_unq UNIQUE(appName, parName);
ALTER TABLE auth.application        ADD CONSTRAINT application_2_ApplicationPartition FOREIGN KEY (parName) REFERENCES auth.ApplicationPartition(parName);

DROP   TABLE IF     EXISTS auth.approle;
CREATE TABLE IF NOT EXISTS auth.approle (
  i                   SERIAL         NOT NULL,
  appName             auth.NameT     NOT NULL,
  roleName            auth.NameT     NOT NULL
);
ALTER TABLE auth.approle             ADD PRIMARY KEY (i);
ALTER TABLE auth.approle             ADD CONSTRAINT approle_appname_roleName_unq UNIQUE(appName, roleName);
ALTER TABLE auth.approle             ADD CONSTRAINT approle_2_application FOREIGN KEY (appName) REFERENCES auth.application(appName);


DROP   TABLE IF     EXISTS auth.appprivilege;
CREATE TABLE IF NOT EXISTS auth.appprivilege (
  i                   SERIAL         NOT NULL,
  appName             auth.NameT     NOT NULL,
  privilegeName       auth.NameT     NOT NULL
);
ALTER TABLE auth.appprivilege        ADD PRIMARY KEY (i);
ALTER TABLE auth.appprivilege        ADD CONSTRAINT appprivilege_appname_privilegename_unq UNIQUE(appName, privilegeName);
ALTER TABLE auth.appprivilege        ADD CONSTRAINT appprivilege_2_application FOREIGN KEY (appName) REFERENCES auth.application(appName);


DROP   TABLE IF     EXISTS auth.approleprivilege;
CREATE TABLE IF NOT EXISTS auth.approleprivilege (
  i                   SERIAL         NOT NULL,
  appName             auth.NameT     NOT NULL,
  roleName            auth.NameT     NOT NULL,
  privilegeName       auth.NameT     NOT NULL
);
ALTER TABLE auth.approleprivilege    ADD PRIMARY KEY (i);
ALTER TABLE auth.approleprivilege    ADD CONSTRAINT approleprivilege_appname_rolename_privilegename_unq UNIQUE (appName, roleName, privilegeName);
ALTER TABLE auth.approleprivilege    ADD CONSTRAINT approleprivilege_2_application   FOREIGN KEY (appName)                REFERENCES auth.application  (appName);
ALTER TABLE auth.approleprivilege    ADD CONSTRAINT approleprivilege_2_approle       FOREIGN KEY (appName, roleName)      REFERENCES auth.approle      (appName, roleName);
ALTER TABLE auth.approleprivilege    ADD CONSTRAINT approleprivilege_2_appprivilege  FOREIGN KEY (appName, privilegeName) REFERENCES auth.appprivilege (appName, privilegeName);

DROP   TABLE IF     EXISTS auth.AuthUserRole;
CREATE TABLE IF NOT EXISTS auth.AuthUserRole (
  i                   SERIAL         NOT NULL,
  appName             auth.NameT     NOT NULL,
  roleName            auth.NameT     NOT NULL,
  userName            auth.NameT     NOT NULL
);
ALTER TABLE auth.AuthUserRole    ADD PRIMARY KEY (i);
ALTER TABLE auth.AuthUserRole    ADD CONSTRAINT AuthUserRole_appName_roleName_userName_unq UNIQUE (appName, roleName, userName);
ALTER TABLE auth.AuthUserRole    ADD CONSTRAINT AuthUserRole_2_application   FOREIGN KEY (appName)                REFERENCES auth.application  (appName);
ALTER TABLE auth.AuthUserRole    ADD CONSTRAINT AuthUserRole_2_approle       FOREIGN KEY (appName, roleName)      REFERENCES auth.approle      (appName, roleName);
ALTER TABLE auth.AuthUserRole    ADD CONSTRAINT AuthUserRole_2_authuser      FOREIGN KEY (userName         )      REFERENCES auth.authuser(userName);

DROP   TABLE IF     EXISTS auth.ssoSession;
CREATE TABLE IF NOT EXISTS auth.ssoSession (
  i                   SERIAL          NOT NULL,
  ssoSessionID        auth.SessionIDT NOT NULL,
  userName            auth.NameT      NOT NULL,
  parName             auth.NameT      NOT NULL,
  createdSSE          auth.SSET       NOT NULL
);
ALTER TABLE auth.ssoSession    ADD PRIMARY KEY (i);
ALTER TABLE auth.ssoSession    ADD CONSTRAINT ssoSession_ssoSessionID_unq       UNIQUE(ssoSessionID);
ALTER TABLE auth.ssoSession    ADD CONSTRAINT ssoSession_2_authuser             FOREIGN KEY (userName)      REFERENCES auth.authuser(userName);
ALTER TABLE auth.ssoSession    ADD CONSTRAINT ssoSession_2_applicationPartition FOREIGN KEY (parName)       REFERENCES auth.applicationPartition  (parName);

DROP   TABLE IF     EXISTS auth.containerSession;
CREATE TABLE IF NOT EXISTS auth.containerSession (
  i                   SERIAL          NOT NULL,
  ssoSessionID        auth.SessionIDT NOT NULL,
  sessionID           auth.SessionIDT NOT NULL,
  userName            auth.NameT      NOT NULL,
  parName             auth.NameT      NOT NULL,
  createdSSE          auth.SSET       NOT NULL
);
ALTER TABLE auth.containerSession    ADD PRIMARY KEY (i);
ALTER TABLE auth.containerSession    ADD CONSTRAINT containerSession_2_ssoSession FOREIGN KEY (ssoSessionID)  REFERENCES auth.ssoSession(ssoSessionID);
ALTER TABLE auth.containerSession    ADD CONSTRAINT containerSession_2_authUser   FOREIGN KEY (userName)      REFERENCES auth.authUser  (username);
ALTER TABLE auth.containerSession    ADD CONSTRAINT containerSession_2_applicationPartition   FOREIGN KEY (parName)   REFERENCES auth.ApplicationPartition(parName);





