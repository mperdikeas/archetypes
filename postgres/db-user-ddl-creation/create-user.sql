DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'gaia-user') THEN

      CREATE ROLE "gaia-user" WITH CREATEDB LOGIN PASSWORD 'gaia-user-pwd';
   END IF;
END
$body$
