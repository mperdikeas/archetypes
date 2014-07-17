#!/usr/bin/env bash
readonly PROGRAM=./numOfLinesIsEven.sh

function printFileInSet {
    declare -a argArray=("${!1}")
    for f in "${argArray[@]}"
    do
        echo $2 file: $f
    done
}


function printEvenOddFilesInDirectory {
    readonly DIRECTORY=$1
    COUNTER=0
    EVEN=0
    ODD=0
    EVENFILES=()
    ODDFILES=()
    for f in $(find $DIRECTORY -type f); do
        #echo file is: $f
        let COUNTER=COUNTER+1
        $PROGRAM $f
        exitCode=$?
        #echo exitCode is: $exitCode
        if [ $exitCode -eq 0 ]
        then
            let EVEN=EVEN+1
            EVENFILES+=($f)
        else
            let ODD=ODD+1
            ODDFILES+=($f)
        fi
    done
    echo $COUNTER files found
    printFileInSet EVENFILES[@] "even set"
    printFileInSet ODDFILES[@] "odd set"
}


printEvenOddFilesInDirectory test-files



