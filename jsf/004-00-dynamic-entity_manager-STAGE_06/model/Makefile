create:
	./create-users-and-databases 1 5
	./users-dml
	psql -U user01 user01db -f ./user-01-dml.sql
	psql -U user02 user02db -f ./user-02-dml.sql
	psql -U user03 user03db -f ./user-03-dml.sql
clean:
	./drop-users-and-databases