q1 = sql.q('SELECT ORDERID FROM PUBLIC.ORDERS')
#STR_1 = '\xce\xa0\xce\xb9 \xce\xad\xce\xbd\xce\xb1'
#STR_1 = 'it can correctly handle latin characters'
STR_1 = '\\u03a0\\u03b9 \\u03ad\\u03bd\\u03b1'
#STR_1 = 'Πι ένα'
I_2=3**2
I_3=2
I_4=I_2**I_3
I_5=q1[1]
q2 = sql.q('SELECT SHIPCOUNTRY FROM PUBLIC.ORDERS WHERE ORDERID=%d'%I_5)
STR_b=q2[1]
