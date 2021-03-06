#!/usr/bin/python

import sys
import string

def tick(astring):
    return "'"+astring+"'"

def username(i):
    return "user"+"%02d"%i

def databasename(i):
    return username(i)+"db"

def ddlForDropDatabaseSchema(i):
    return "DROP DATABASE "+databasename(i)+";"

def ddlForDropUser(i):
    return "DROP USER "+username(i)+";"

if (len(sys.argv)!=3):
    print "usage is: "+sys.argv[0]+" <range-start> <range-end>"
    sys.exit(1)

range_start = string.atoi(sys.argv[1])
range_end   = string.atoi(sys.argv[2])

for i in range(range_start, range_end):
    print ddlForDropDatabaseSchema(i)
    print ddlForDropUser(i)
