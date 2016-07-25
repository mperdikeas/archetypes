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

-- nothing yet

INSERT INTO typea.person(fname, lname, comments, yearOfBirth) VALUES
('John'    , 'Smith'       , 'a nondescript guy'           , 1980),
('Mike'    , 'Peluta',       'better give him a wide berth', 1963);





