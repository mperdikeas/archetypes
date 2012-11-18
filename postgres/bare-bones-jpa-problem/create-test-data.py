#!/usr/bin/python
# this python script is synchronized with schema-dml-test.sql
import sys
from random import choice
from random import randint
from datetime import date
from pprint import pprint
from datetime import timedelta
from math import ceil
import math
import random
import codecs



if len(sys.argv)!=3:
    sys.exit("usage is :"+sys.argv[0]+" number-of-a number-of-b")

aCount = int(sys.argv[1])
bCount = int(sys.argv[2])


def randomline (i):
    return "INSERT INTO a(id, a1) VALUES(%d, %s);" % (i, "\'table a row: # %d\'" % i)


def randomlineB (i, j):
    def rowIndex(i, j):
        return i*bCount+j
    return "INSERT INTO b(id, b1, a) VALUES(%d, %s, %d);" % (rowIndex(i,j) , ("\'table b row: # %d (%dth child) hanging under table a row %d\'" % (rowIndex(i,j),j, i)), i)

for i in range(aCount):
    line = randomline(i)
    print(line)
    for j in range(bCount):
        line = randomlineB(i,j)
        print(line)
    #print(randomline(i))
    #fscript.write(randomline(i)+'\n')

sys.exit(0)

