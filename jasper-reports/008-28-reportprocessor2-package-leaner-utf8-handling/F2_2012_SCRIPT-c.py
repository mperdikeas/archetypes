############## PARAMETROI     
I_peri_id = 1
I_comp_id = 1
D_date_from = date(2012, 1, 1)
###I_year = D_date_from.year %100
###I_month= D_date_from.month
###I_day  = D_date_from.day
###D_date_to = date.today()
D_date_to =date(2012, 12, 31)
I_rtfe_session_id = 10
I_rtfh_id = 8
I_liquidation_rfch_id = 0
I_increment_percent = 0
I_rerp_id = 0
I_modifiable = 0
I_payment_value = 0
I_external  = 0
I_month_intra  = 12
STR_exempt ='APPALAGH LOGV MH 2012'
B_reserved = True
I_modifiable = 0
########################################
################ rd company info #####################
comp_sqls = """
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
RD_COMP = sqls(comp_sqls%(I_comp_id))
###RD_P_FPA = sqlm('SELECT ratf_code AS ratf_code,SUM (retd_net_value) AS retd_net_value,SUM (retd_vat_value) AS retd_vat_value,SUM (retd_gross_value) AS retd_gross_value FROM v_p_fpa_values WHERE PERI_ID = %d  AND reth_issue_date BETWEEN \'%s\' AND \'%s\' group by ratf_code order by ratf_code'%(I_peri_id, D_date_from.strftime("%Y-%m-%d"), D_date_to.strftime("%Y-%m-%d")), num=sys.maxint)
p_pfa_sqls = """
SELECT
SUM (retd_net_value) retd_net_value,
SUM (retd_vat_value) retd_vat_value,
SUM (retd_gross_value) retd_gross_value 
FROM v_p_fpa_values 
WHERE PERI_ID = %d
AND reth_issue_date BETWEEN \'%s\' AND \'%s\'
AND ratf_code =\'%s\'   
"""
##actualNumOfUsers = RD_P_FPA.len()
###################################################AND ratf_code =\'%s\'
STR_P001a = RD_COMP.TAOF_DESCRIPTION
STR_P001b =RD_COMP.TAOF_CODE
STR_P004d =  date.today().strftime("%d")
STR_P004m =  date.today().strftime("%m")
STR_P004y =  date.today().strftime("%y")
STR_P006 = D_date_from.strftime("%Y") 
STR_P007a_d   = D_date_from.strftime("%d")
STR_P007a_m   = D_date_from.strftime("%m")
STR_P007a_y   = D_date_from.strftime("%y")
STR_P007b_d   = D_date_to.strftime("%d")
STR_P007b_m   = D_date_to.strftime("%m")
STR_P007b_y   = D_date_to.strftime("%y")
if (D_date_to.month - D_date_from.month==0):
    difmon = D_date_from.month
    STR_P008a = 'X'.rjust(difmon)
else:
    difmon = D_date_to.month/3
    STR_P008b = 'X'.rjust(difmon)
I_P009 = I_month_intra
if (I_modifiable==1):
    STR_P010 = 'X'
else:
    STR_P010 = ' '
if (B_reserved):
    STR_P011 = 'X'
else:
    STR_P011 = ' '
if (I_external != 9):
    STR_P012 ='X'.rjust(I_external)
else:
    STR_P012 =' '
STR_P013 = STR_exempt
STR_P101 =RD_COMP.COMP_DESCRIPTION
STR_P102 =RD_COMP.COMP_FIRST_NAME
STR_P103 =RD_COMP.COMP_FATHER_NAME
STR_P104 =RD_COMP.COMP_TIN


p_pfa_sqlm_MP = """
SELECT
ratf_code,
retd_net_value
FROM v_p_fpa_values 
WHERE PERI_ID = %d
AND reth_issue_date BETWEEN \'%s\' AND \'%s\'
"""

p_pfa_sqlm_MP_filled = p_pfa_sqlm_MP%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"))
u.println('results query is : %s'%p_pfa_sqlm_MP_filled)
results = sqlm(p_pfa_sqlm_MP_filled)
u.println('CODE #1 IS %s'%results[1].S_RATF_CODE)
u.println('CODE #2 IS %s'%results[2].S_RATF_CODE)
u.println('CODE #3 IS %s'%results[3].S_RATF_CODE)


#for i in range(results.len()):
#    u.println('external loop %d of %d'%(i, results.len()))
#    for k in results[i+1].keys():
#        u.println('k = %s'%k)

#materialize('BD_P', results, 'RATF_CODE', 'RETD_NET_VALUE')
materialize('BD_P', results, 'RATF_CODE', 'RETD_NET_VALUE', NVLZ)

##########################
#      ekroes            #
########################## P301  
RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'301'))
BD_P301=RD_P_FPA.RETD_NET_VALUE
BD_P331=BD_P301*.13
BD_P307 = RD_P_FPA.RETD_NET_VALUE
SALES_VAT =RD_P_FPA.RETD_VAT_VALUE
########################## P302  
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'302'))
##BD_P302=RD_P_FPA.RETD_NET_VALUE
BD_P332=BD_P302*.065
BD_P307 = BD_P307+RD_P_FPA.RETD_NET_VALUE
SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P303  
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'303'))
##BD_P303=RD_P_FPA.RETD_NET_VALUE
BD_P333=BD_P303*.23
BD_P307 = BD_P307+RD_P_FPA.RETD_NET_VALUE
SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P304
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'304'))
##BD_P304=RD_P_FPA.RETD_NET_VALUE
###BD_P334=BD_P304*.09
## BD_P307 = BD_P307 + RD_P_FPA.RETD_NET_VALUE
####SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P305
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'305'))
##BD_P305=RD_P_FPA.RETD_NET_VALUE
###BD_P335=BD_P305*.05
###BD_P307 = BD_P307+RD_P_FPA.RETD_NET_VALUE
###BD_SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P306  
RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'306'))
BD_P306=RD_P_FPA.RETD_NET_VALUE
BD_P336=NVLZ(BD_P306)*.23
##BD_P307 = BD_P307+RD_P_FPA.RETD_NET_VALUE
##BD_SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P308  
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'308'))
##BD_P308=RD_P_FPA.RETD_NET_VALUE
BD_P311 = RD_P_FPA.RETD_NET_VALUE
##BD_SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P309  
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'309'))
##BD_P309=RD_P_FPA.RETD_NET_VALUE
##BD_P311 = BD_P311+RD_P_FPA.RETD_NET_VALUE
###BD_SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
########################## P310  
##RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),'310'))
##BD_P310=RD_P_FPA.RETD_NET_VALUE
###BD_P311 = BD_P311+RD_P_FPA.RETD_NET_VALUE
##BD_SALES_VAT =SALES_VAT+RD_P_FPA.RETD_VAT_VALUE
I_ZZ = ceiling(3.1)
STR_ZZ = 'a b'
