#!/usr/bin/env bash

set -e
set -u

if [ $# -lt 5 ]
then 
    printf "scripts expects at least 5 options, yet received only $#\n"
    exit 1
fi



USER=$1
DB=$2
PWD=$3
DDL_DML_SWITCH=$4
DDL_FILE=$5


load_dml_data=false

DDL_ONLY="ddl-only"
DDL_DML="ddl-dml"

if [ "$DDL_DML_SWITCH" == "$DDL_ONLY" ]
then
    load_dml_data=false
else
    if [ "$DDL_DML_SWITCH" == "$DDL_DML" ]
    then
        load_dml_data=true
        DML_FILE=$6
    else
        echo "Unrecognized option: '$5'. Recognized values are: '$DDL_ONLY' and '$DDL_DML'"
        exit 1
    fi
fi

printf "DDL_DML_SWITCH evaluated as $load_dml_data\n"





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


fpsqlf $USER $DB ./$DDL_FILE
if [ "$load_dml_data" = true ] ; then
    fpsqlf $USER $DB ./$DML_FILE
fi

