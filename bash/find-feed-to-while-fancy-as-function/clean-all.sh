#!/usr/bin/env bash



source call-ant-targets-fun.sh
if call-ant-targets clean "." ; then
    exit 0
else
    exit 1
fi


