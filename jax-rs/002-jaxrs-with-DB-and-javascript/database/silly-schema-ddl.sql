DROP SCHEMA IF EXISTS silly_schema CASCADE;
CREATE SCHEMA silly_schema;

SET CLIENT_ENCODING TO 'latin1' ;

SET statement_timeout = 0;
-- SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;
SET search_path = silly_schema;

CREATE DOMAIN silly_schema.keyType AS VARCHAR(50);
CREATE DOMAIN silly_schema.valueType AS TEXT;


DROP TABLE IF EXISTS silly_schema.keyvalue;
CREATE TABLE IF NOT EXISTS silly_schema.keyvalue (
       key      keyType   NOT NULL,
       value    valueType NOT NULL
);
ALTER TABLE silly_schema.keyvalue ADD PRIMARY KEY (key);
