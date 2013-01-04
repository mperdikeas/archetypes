create:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./schema-ddl.sql

load:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./schema-ddl.sql
	./execute-script-as-gaia-user ./schema-test-dml.sql

clean:
	./execute-script-as-postgres ./drop-database-and-user.sql	