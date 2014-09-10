#!/usr/bin/env bash



source call-ant-targets-fun.sh
if call-ant-targets build "project-a project-c project-delta-delta-delta-delta" ; then
    exit 0
else
    exit 1
fi



