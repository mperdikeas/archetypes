#!/usr/bin/python
import datetime

def generate_data(num_of_rows):
    print 'INSERT INTO A VALUES(1,\'A VALUE OF 1\')'
    i = 0
    while (i < num_of_rows):
        print 'INSERT INTO B VALUES('+str(i)+',1,\'a value on the memory table B of '+str(i)+')'
        i+=1

generate_data(1000000)
