DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'throwAwayTestUser') THEN

      CREATE ROLE "throwAwayTestUser" WITH CREATEDB LOGIN PASSWORD 'throwAwayTestUser-pwd';
   END IF;
END
$body$
