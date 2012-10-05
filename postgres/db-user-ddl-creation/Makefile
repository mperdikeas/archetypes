create:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./cashflow-ddl.sql

load:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./cashflow-ddl.sql
	./execute-script-as-gaia-user ./schema-dml-test.sql

clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	