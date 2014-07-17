#!/usr/bin/env bash
FILE=$1
LINES=$(wc -l < "$FILE")
# echo num of lines in file $FILE is :$LINES
N=$((  $LINES%2  ))
# echo modulo 2 is $N
if [ $N -eq 0 ]
    then exit 0
    else exit 1
fi