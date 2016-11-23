SET CLIENT_ENCODING TO 'latin1' ;

SET statement_timeout = 0;
-- SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;
SET search_path = doesnotexist;



INSERT INTO auth.authuser (username, email, fname, lname, hashAlgo, salt, password) VALUES
('mperdikeas', 'mperdikeas@gmail.com', 'Menelaus', 'Perdikeas', 'md256sum', 'foo', 'foo');




