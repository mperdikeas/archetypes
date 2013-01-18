#!/usr/bin/python

import sys, string, os#, arcgisscripting


if len(sys.argv)!=2:
    sys.exit("usage is :"+sys.argv[0]+" number-of-iterations")

numOfIterations = int(sys.argv[1])


for i in range(numOfIterations):
    if (i % 10 == 0):
        print( '%d iterations' % i )
    os.system( 'curl  http://mperdikeas:8080/gsiswsdlbridge/afm/version'   )
    os.system( 'curl  http://mperdikeas:8080/gsiswsdlbridge/afm/047787907' )

