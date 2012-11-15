create:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./ab-ddl.sql

load:
	./execute-script-as-postgres ./drop-database-and-user.sql
	./execute-script-as-postgres ./create-user.sql
	./execute-script-as-postgres ./create-database.sql
	./execute-script-as-gaia-user ./ab-ddl.sql
	./execute-script-as-gaia-user ./small

