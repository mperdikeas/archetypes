#!/usr/bin/env bash
me=`basename $0`
whereILive=${BASH_SOURCE[0]/%${me}/.}
echo "I live in ${whereILive}"

echo "I am able to cat the contents of the following file no matter where you call me from"
cat ${whereILive}/a/b/c/d.txt

echo "I am ONLY able to cat the contents of the following file only if you call me from the location where I live"
cat ./a/b/c/d.txt

