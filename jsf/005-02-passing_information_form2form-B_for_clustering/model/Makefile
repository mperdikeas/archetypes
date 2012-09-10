create:
	psql -U postgres postgres -c "CREATE ROLE jsf005 WITH CREATEDB LOGIN PASSWORD 'jsf005';"
	psql -U postgres postgres -c "CREATE DATABASE jsf005db WITH OWNER jsf005;"
	psql -U jsf005 jsf005db -f jsf005db-ddl.sql
	psql -U jsf005 jsf005db -f jsf005db-dml.sql

clean:
	psql -U postgres postgres -c "DROP DATABASE jsf005db;"
	psql -U postgres postgres -c "DROP USER jsf005;"
