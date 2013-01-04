SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

CREATE SEQUENCE testdata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2140000000
    CACHE 1;

CREATE TABLE testdata (
    id INTEGER       DEFAULT nextval('testdata_id_seq'::regclass) NOT NULL,
     a INTEGER,
     b VARCHAR,
     c NUMERIC(20,10),
     d TIMESTAMP WITH TIME ZONE,
     e TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE ONLY testdata ADD CONSTRAINT id_pk PRIMARY KEY (id);

ALTER SEQUENCE testdata_id_seq OWNED BY testdata.id;

ALTER TABLE testdata OWNER TO "gaia-user";
