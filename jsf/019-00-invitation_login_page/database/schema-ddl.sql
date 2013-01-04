SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

CREATE TABLE invitation (
    id         INTEGER                      NOT NULL,
    email      CHARACTER VARYING (120 )     NOT NULL,
    guid       CHARACTER VARYING (1000)     NOT NULL,
    valid_till TIMESTAMP (0) WITH TIME ZONE NOT NULL
);

CREATE SEQUENCE invitation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2140000000
    CACHE 1;

ALTER SEQUENCE invitation_id_seq OWNED BY invitation.id;

CREATE TABLE gaiauser (
    id          INTEGER                      NOT NULL,
    email       CHARACTER VARYING (120)      NOT NULL,
    firstname   CHARACTER VARYING (120)      NOT NULL,
    lastname    CHARACTER VARYING (120)      NOT NULL,
    membersince TIMESTAMP (0) WITH TIME ZONE NOT NULL
);

CREATE SEQUENCE gaiauser_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2140000000
    CACHE 1;

ALTER SEQUENCE gaiauser_id_seq OWNED BY gaiauser.id;


