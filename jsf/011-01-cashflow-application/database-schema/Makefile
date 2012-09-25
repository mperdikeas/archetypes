create:
	./execute-script-as-postgres  ./drop-database-and-user.sql
	./execute-script-as-postgres  ./create-database-and-user.sql
	./execute-script-as-gaia-user ./cashflow-ddl.sql


clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	