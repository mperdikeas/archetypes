load:
	psql -p 5433 -U postgres postgres -f  ./drop-database-and-user.sql
	psql -p 5433 -U postgres postgres -f  ./create-user.sql
	psql -p 5433 -U postgres postgres -f  ./create-database.sql
	psql -p 5433 -U throwAwayTestUser throwAwayTestDB -f $1 ./cashflow-ddl.sql
	psql -p 5433 -U throwAwayTestUser throwAwayTestDB -f $1 ./schema-dml-test.sql

clean:
	psql -p 5433 -U postgres postgres -f  ./drop-database-and-user.sql

