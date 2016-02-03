#!/usr/bin/env bash

set -e
set -u

USER=$1
DB=$2
PWD=$3

load_dml_data=true

if [ $# -gt 3 ]
then 
    if [ "$4" == "ddl-only" ]
    then
        load_dml_data=false
    else
        if [ "$4" == "ddl-dml" ]
        then
            load_dml_data=true
        else
            echo "unrecognized option: $4"
            exit 1
        fi
    fi
fi

#echo "user is: $USER, database is: $DB and password is: $PWD"

source psql_functions.sh



fpsql postgres postgres <<EOF
DO
\$body\$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = '$USER') THEN
      CREATE ROLE "$USER" WITH CREATEDB LOGIN PASSWORD '$PWD';
   END IF;
END
\$body\$
EOF

fpsql postgres postgres <<EOF
CREATE DATABASE "$DB" WITH OWNER "$USER";
EOF


fpsqlf $USER    $DB ./silly-schema-ddl.sql
if [ "$load_dml_data" = true ] ; then
    fpsqlf $USER    $DB ./silly-schema-dml.sql
fi


fpsql  $USER    $DB <<EOF
ALTER USER "$USER" SET search_path TO silly_schema, public;
EOF



