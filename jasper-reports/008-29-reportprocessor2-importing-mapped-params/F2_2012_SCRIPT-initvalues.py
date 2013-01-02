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



################################################################################
defineFormParam("I_formInt", 23)
defineFormParam("formStr", "broo ha ha")

# mp params
I_AAA = I_formInt
STR_BBB = formStr
defineFormParam("STR_CCC", "dddd")
################################################################################
################ rd company info #####################
comp_sqls = """
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
RD_COMP = sqls(comp_sqls%(I_comp_id))
RD_REEM = sqlm('SELECT ratf_code,SUM (retd_net_value) AS retd_net_value,SUM (retd_vat_value) AS retd_vat_value,SUM (retd_gross_value) AS retd_gross_value FROM v_p_fpa_values WHERE PERI_ID = %d  AND reth_issue_date BETWEEN \'%s\' AND \'%s\' group by ratf_code order by ratf_code'%(I_peri_id, D_date_from.strftime("%Y-%m-%d"), D_date_to.strftime("%Y-%m-%d")), num=sys.maxint)
actualNumOfUsers = RD_REEM.len()
I_test = actualNumOfUsers
###################################################
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
STR_P012 = STR_exempt
STR_P101 =RD_COMP.COMP_DESCRIPTION
STR_P102 =RD_COMP.COMP_FIRST_NAME
STR_P103 =RD_COMP.COMP_FATHER_NAME
STR_P104 =RD_COMP.COMP_TIN
STR_testingAnyToString1 = AnyToString('alpha')
STR_testingAnyToString2 = AnyToString(u'άλφα')
STR_testingAnyToString3 = AnyToString(1)
STR_testingAnyToString4 = AnyToString(1.3)
STR_testingAnyToString5 = AnyToString(date(2012,2,2))
