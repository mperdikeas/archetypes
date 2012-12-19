############## PARAMETROI     
I_peri_id = 1
I_comp_id = 1
D_date_from = date(2012, 01, 1)
D_date_to =date(2012, 03, 31)
STR_RERP_FIRST_NAME = 'aaa'
STR_RERP_LAST_NAME ='bbb'
STR_RERP_TIN='ccc'
########################################
################ rd company info #####################
comp_sqls = """
select reei_description from er_re_e3_invoices
"""
results = sqlm(comp_sqls)
for i in xrange(1, results.len()+1):
    a = unicode(results[i][1], 'foo')
    #u.println(results[i][1])
    u.println(a)
