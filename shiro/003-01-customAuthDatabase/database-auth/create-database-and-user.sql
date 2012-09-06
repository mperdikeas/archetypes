CREATE ROLE "gaia-user" WITH CREATEDB LOGIN PASSWORD 'gaia-user-pwd';
CREATE DATABASE "gaia-auth" WITH OWNER "gaia-user";