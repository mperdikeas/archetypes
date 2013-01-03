create:
	./execute-script-as-postgres ./drop-database.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./schema-ddl.sql


clean:
	./execute-script-as-postgres ./drop-database.sql