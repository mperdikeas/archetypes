q1s = """
SELECT 
      MIN(USER_ID)
      foo
      FROM
      SS_USERS
"""
#q1 = sqls('SELECT MIN(USER_ID) foo FROM SS_USERS')
q1 = sqls(q1s)
if (q1[1]==1):
    BD_a = 3.234
else:
    BD_a = 23.320000
BD_b = BD_a * 1.25
STR_b = '%.5g'%(BD_b)
STR_bz = '%.5f'%(BD_b)
STR_Z=str(q1.foo)
STR_1 = 'Πάϊ ένα'
I_2=3*2
I_3=2
I_4=I_2*(I_3+I_3)
I_5=q1[1]
q2 = sqls('SELECT user_nickname FROM SS_USERS WHERE user_id=%d'%I_5)
q3 = sqls('SELECT USER_ID FROM SS_USERS WHERE user_nickname=\'%s\''%q2[1])
STR_b=q2[1]
STR_C=str(q3[1])
q4 = sqls('SELECT user_surname FROM SS_USERS WHERE user_nickname=\''+q2[1]+'\'')
STR_D = q4[1]
q5 = sqls('SELECT user_firstname FROM SS_USERS WHERE user_nickname=\'Βασιλάκης\'')
STR_D1 = q5[1]
STR_D1 = str(q5[1])
STR_D1 = q2.user_nickname
q6 = sqls('SELECT user_email FROM SS_USERS WHERE user_nickname=\''+STR_D1+'\'')
STR_E=q5[1]
STR_F=q6.user_email
q7 = sqlm('SELECT user_nickname ypokoristiko FROM SS_USERS', 5, False)
STR_USER1 = q7[1][1]
STR_USER2 = q7[2].ypokoristiko
STR_USER3 = q7[3][1]
STR_USER4 = q7[4][1]
if q7.len()>=5:
    STR_USER5 = q7[5][1]
if q7.len()>=6:
    STR_USER6 = q7[6][1]
else:
    STR_USER6 = "κενό"
