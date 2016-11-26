include ${CREDNTLS}
ifndef DB
    $(error DB is not set)
endif
ifndef USER
    $(error USER is not set)
endif
ifndef PWD
    $(error PWD is not set)
endif
ifndef DDL_FILE
    $(error DDL_FILE is not set)
endif
.PHONY: all drop-db create-db graph clean check-env
all: drop-db create-db graph
drop-db: check-env
	./drop-user-database.sh  $(USER) $(DB)
create-db: check-env
        ifndef DML_FILE
		./create-user-database.sh $(USER) $(DB) $(PWD) ddl-only $(DDL_FILE)
        else
		./create-user-database.sh $(USER) $(DB) $(PWD) ddl-dml $(DDL_FILE) $(DML_FILE)
        endif
graph: check-env
	java -jar ./schemaSpy_5.0.0.jar -t pgsql -host localhost -db $(DB) -s $(SCHEMA_TO_GRAPH) -u $(USER) -p $(PWD) -o schema-viz -dp ./postgresql-9.1-901.jdbc4.jar
clean: check-env
	./drop-user-database.sh $(USER) $(DB)
	rm -fr schema-viz
check-env:
ifndef CREDNTLS
	$(error CREDNTLS is undefined)
endif
