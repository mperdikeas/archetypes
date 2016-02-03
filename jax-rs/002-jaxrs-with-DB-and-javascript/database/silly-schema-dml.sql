SET CLIENT_ENCODING TO 'latin1' ;

SET statement_timeout = 0;
-- SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;
SET search_path = silly_schema;

INSERT INTO silly_schema.keyvalue (key, value) VALUES
('foo', 'value for key foo'),
('boo', 'value for key boo'),
('zar', 'value for key zar')
;

