q1 = sql.q('SELECT MIN(USER_ID) FROM SS_USERS')
STR_1 = 'Πάϊ ένα'
I_2=3**2
I_3=2
I_4=I_2**I_3
I_5=q1[1]
q2 = sql.q('SELECT user_nickname FROM SS_USERS WHERE user_id=%d'%I_5)
q3 = sql.q('SELECT USER_ID FROM SS_USERS WHERE user_nickname=\'%s\''%q2[1])
STR_b=q2[1]
STR_C=str(q3[1])
q4 = sql.q('SELECT user_surname FROM SS_USERS WHERE user_nickname=\''+q2[1]+'\'')
STR_D = q4[1]
q5 = sql.q('SELECT user_firstname FROM SS_USERS WHERE user_nickname=\'Βασιλάκης\'')
STR_D1 = q5[1]
STR_D1 = str(q5[1])
#STR_D1 = str(q2[1]) # this throws: UnicodeEncodeError: 'ascii' codec can't encode characters in position 0-8: ordinal not in range(128)
STR_D1 = str(sql.escape(q2[1])) # this works
q6 = sql.q('SELECT user_email FROM SS_USERS WHERE user_nickname=\''+STR_D1+'\'')
STR_E=q5[1]
STR_F=q6[1]
