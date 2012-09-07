create:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-database-and-user.sql
	./execute-script-as-gaia-user ./auth-ddl.sql
	./execute-script-as-gaia-user ./auth-dml.sql

clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	