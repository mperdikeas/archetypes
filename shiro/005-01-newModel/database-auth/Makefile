create:
	./execute-script-as-postgres  ./drop-database-and-user.sql
	./execute-script-as-postgres  ./create-database-and-user.sql
	./execute-script-as-gaia-user ./auth-ddl-dml.sql
	./execute-script-as-gaia-user ./auth-sample-user-data-dml.sql


clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	