class MyRecord:
    def __init__(self, dictData):
        self._dictData = dictData
    def __getattr__(self, nameOfField):
        global STR_debug
        STR_debug = nameOfField
        return self._dictData.f(nameOfField)
    def __getitem__(self, idx):
        return self._dictData[idx]


class MySQLrecords:
    def __init__(self, sqlData):
        self._results = sqlData
    def __getitem__(self, idx):
        return MyRecord(self._results[idx])

def sqlm(query, num, tolerance):
    return MySQLrecords(sql.qm(query, num, tolerance))

def sqls(query):
    return MyRecord(sql.q(query))

q1 = sqls('SELECT MIN(USER_ID) foo FROM SS_USERS')
STR_E=str(q1.foo)
q7 = sqlm('SELECT user_nickname ypokoristiko FROM SS_USERS', 5, False)
STR_USER2 = q7[2].ypokoristiko
STR_USER3 = q7[2][1]
