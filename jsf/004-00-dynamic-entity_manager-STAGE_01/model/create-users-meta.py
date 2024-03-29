#!/usr/bin/python

import sys
import string



def tick(astring):
    return "'"+astring+"'"

def username(i):
    return "user"+"%02d"%i

def ddlForCreateUser(i):
    return "CREATE ROLE "+username(i)+" WITH CREATEDB LOGIN PASSWORD "+tick(username(i))+";"

if (len(sys.argv)!=3):
    print "usage is: "+sys.argv[0]+" <range-start> <range-end>"
    sys.exit(1)

range_start = string.atoi(sys.argv[1])
range_end   = string.atoi(sys.argv[2])

for i in range(range_start, range_end):
    print ddlForCreateUser(i)
