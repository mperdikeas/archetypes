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



if len(sys.argv)!=4:
    sys.exit("usage is :"+sys.argv[0]+" fname number-of-a number-of-b")

fnamestr = sys.argv[1]
aCount = int(sys.argv[2])
bCount = int(sys.argv[3])

fname = codecs.open(fnamestr, encoding='utf-8', mode='w+')

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

for line in f:
    print line

def typeAndGroup(s):
    ss = s.split(',')
    if (len(ss)!=2):
        print "not 2 groups found in line: "+s
        exit(1)
    return (int(ss[0]), ss[1])

tgdict={} # type and list of group names belonging to that type

with codecs.open(fname, encoding='utf-8', mode='r') as f:
    for line in f:
        gr_type, gr_name = typeAndGroup(line.strip())
        if gr_type not in tgdict:
            tgdict[gr_type]=[]
        tgdict[gr_type].append(gr_name.strip())
    pprint(tgdict)

def serialDate(i):
    daysahead = i / TRANSACTIONS_PER_DAY
    date = START_DATE+timedelta(days=daysahead)
    return date

def randomTypology():
    typologies=[(1,0),(0,0),(0,-1)]
    return choice(typologies)

#    if bool(random.getrandbits(1)):
#       return (0,-1)
#   else:
#       return (1,0)
    
def choiceExcept(listofvalues, valueexcept):
    llistofvalues = list(listofvalues)
    if (llistofvalues.count(valueexcept)>0):
        #print 'llistofvalues is: '+str(llistofvalues)
        #print 'valueExcept is: '+valueexcept
        llistofvalues.remove(valueexcept)
        #print 'new listofvalues is: '+str(llistofvalues)
    return choice(llistofvalues)

def randomline(i):
    typeA,typeB = randomTypology()
    #print "typology randomly selected: %d -> %d" % (typeA, typeB)
    typeAGrp = choice(tgdict.get(typeA))
    #print "typeAGrp is: "+typeAGrp
    typeBGrp = choiceExcept(tgdict.get(typeB), typeAGrp)
    if (typeAGrp==typeBGrp):
        raise Exception(typeAGrp+'=='+typeBGrp)
    return typeAGrp+','+typeBGrp+','+serialDate(i).strftime('%Y%m%d')+','+str(randint(1,12000))+'.'+str(randint(0,100))+', silly description'

for i in range(NOFTRANSACTIONS):
    #print(randomline(i))
    fscript.write(randomline(i)+'\n')


