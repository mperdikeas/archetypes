DROP SCHEMA IF EXISTS typea CASCADE;
CREATE SCHEMA typea;

SET CLIENT_ENCODING TO 'latin1' ;

SET statement_timeout = 0;
-- SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

-- if using PostgreSQL > 9.1
-- SET search_path = doesnotexist;
SET search_path = typea; -- this is not a best practice, use the line above if your PostgreSQL version allows it


CREATE DOMAIN typea.NameT          AS VARCHAR(50);
CREATE DOMAIN typea.DescrT         AS VARCHAR(200);
CREATE DOMAIN typea.YearOfBirthT   AS INTEGER CHECK ((value >= 1900) AND (value<= 2020));


DROP TABLE   IF     EXISTS typea.person;
CREATE TABLE IF NOT EXISTS typea.person (
  i               SERIAL             NOT NULL,
  fname           typea.NameT        NOT NULL,
  lname           typea.NameT        NOT NULL,
  comments        typea.DescrT       NOT NULL,
  yearOfBirth     typea.YearOfBirthT NOT NULL
);
ALTER TABLE typea.person ADD PRIMARY KEY (i);
ALTER TABLE typea.person ADD CONSTRAINT fname_lname_unique UNIQUE(fname, lname);

