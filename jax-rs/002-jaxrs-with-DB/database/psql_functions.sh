#!/usr/bin/env bash

function fpsql {
    FUSER=$1
    FDB=$2
    PGOPTIONS='--client-min-messages=warning' psql -v ON_ERROR_STOP=1 --quiet -X -U $FUSER  --no-password -d $FDB
    # for more verbose output:
    # psql -a -v ON_ERROR_STOP=1 -X -U $FUSER -d $FDB
}

function fpsqlf {
    FUSER=$1
    FDB=$2
    FSCRIPT=$3
    PGOPTIONS='--client-min-messages=warning' psql -v ON_ERROR_STOP=1 --quiet -X -U $FUSER  --no-password -d $FDB -f $FSCRIPT
    # for more verbose output:
    # psql -a -v ON_ERROR_STOP=1 -X -U $FUSER -d $FDB -f $FSCRIPT
}
