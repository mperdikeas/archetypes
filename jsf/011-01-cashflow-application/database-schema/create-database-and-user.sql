CREATE ROLE "gaia-user" WITH CREATEDB LOGIN PASSWORD 'gaia-user-pwd';
CREATE DATABASE "gaia-cashflow" WITH OWNER "gaia-user";