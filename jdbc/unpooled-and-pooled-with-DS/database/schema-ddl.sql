SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

CREATE SEQUENCE variousdatetimes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2140000000
    CACHE 1;

CREATE TABLE variousdatetimes (
    id INTEGER       DEFAULT nextval('variousdatetimes_id_seq'::regclass) NOT NULL,
    t1 TIMESTAMP     WITHOUT TIME ZONE,
    t2 TIMESTAMP     WITH    TIME ZONE,
    t3 TIMESTAMP (0) WITH    TIME ZONE,
    t4 TIMESTAMP (1) WITH    TIME ZONE
);

ALTER TABLE ONLY variousdatetimes ADD CONSTRAINT id_pk PRIMARY KEY (id);

ALTER SEQUENCE variousdatetimes_id_seq OWNED BY variousdatetimes.id;

ALTER TABLE variousdatetimes OWNER TO "gaia-user";
