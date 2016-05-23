#!/usr/bin/env bash

set -e
set -u

USER=$1
DB=$2

#echo "user is: $USER, database is: $DB"

source psql_functions.sh



fpsql postgres postgres <<EOF
DROP DATABASE IF EXISTS "$DB";
DROP USER IF EXISTS "$USER";
EOF