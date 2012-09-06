create:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-database-and-user.sql
	./execute-script-as-gaia-user ./auth-ddl.sql

clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	