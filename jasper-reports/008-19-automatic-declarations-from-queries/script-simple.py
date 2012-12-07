q1s = """
SELECT 
      MIN(USER_ID)
      foo
      FROM
      SS_USERS
"""
q1 = sqls(q1s)
I_1=q1[1]
q2 = sqls('SELECT user_nickname STR_USER_NICKNAME_FROM_QUERY FROM SS_USERS WHERE user_id=%d'%I_1)
STR_a=q2[1]
STR_b=q2.STR_USER_NICKNAME_FROM_QUERY
q3= sqlm('SELECT user_nickname ypokoristiko FROM SS_USERS', num=sys.maxint)
STR_cUSER1 = q3[1][1]
STR_cUSER2 = q3[2].YPOKORISTIKO
STR_cUSER3 = q3[3][1]
#STR_james=3
