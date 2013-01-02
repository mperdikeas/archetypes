############## PARAMETROI     
I_peri_id = 1
I_comp_id = 1
D_date_from = date(2012, 01, 1)
###I_year = D_date_from.year %100
###I_month= D_date_from.month
###I_day  = D_date_from.day
###D_date_to = date.today()
D_date_to =date(2012, 03, 31)
I_rtfe_session_id = 10
I_rtfh_id = 8
I_last_rfch_id =0
I_liquidation_rfch_id = 0
I_increment_percent = 0
I_rerp_id = 0
I_modifiable = 0
B_has_dosage = False
I_payment_value = 0
I_external  = 4
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
###RD_P_FPA = sqlm('SELECT ratf_code AS ratf_code,SUM (retd_net_value) AS retd_net_value,SUM (retd_vat_value) AS retd_vat_value,SUM (retd_gross_value) AS retd_gross_value FROM v_p_fpa_values WHERE PERI_ID = %d  AND reth_issue_date BETWEEN \'%s\' AND \'%s\' group by ratf_code order by ratf_code'%(I_peri_id, D_date_from.strftime("%Y-%m-%d"), D_date_to.strftime("%Y-%m-%d")), num=sys.maxint)
############################
#  REV_EXP VALUES          #
############################
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
def F_RD_P_FPA(RATF_CODE, RATF_CODE2,pref, coefficient):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
    globals()['BD_P' + str(RATF_CODE2)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)*coefficient
    globals()[pref] += NVLZ(RD_P_FPA.RETD_VAT_VALUE)
def F_RD_P_FPA_2(RATF_CODE,pref):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
    globals()[pref] += NVLZ(RD_P_FPA.RETD_VAT_VALUE)
def F_RD_P_FPA_3(RATF_CODE):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
def MAKE_STR(POS):
    globals()['STR_P' + str(POS)] = 'X'
##############################
## RD PREVIOUS VAT PERIOD  ###
##############################
prev_per_sqlm = 'SELECT rfcd_value  FROM er_re_form_considered_details WHERE rfch_id=%d and rfcd_rtfd_code =\'%s\''
###
###################################################AND ratf_code =\'%s\'
RD_COMP = sqls(comp_sqls%(I_comp_id))
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
    #STR_P008a = 'X'.rjust(difmon)
    MAKE_STR('008a_'+str(difmon))
else:
    difmon = D_date_to.month/3
    ###STR_P008b = 'X'.rjust(difmon)
    MAKE_STR('008b_'+str(difmon))
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
    #STR_P012 ='X'.rjust(I_external)
    MAKE_STR('012_'+str(I_external))
else:
    STR_P012 =' '
STR_P013 = STR_exempt
STR_P101 =RD_COMP.COMP_DESCRIPTION
STR_P102 =RD_COMP.COMP_FIRST_NAME
STR_P103 =RD_COMP.COMP_FATHER_NAME
STR_P104 =RD_COMP.COMP_TIN
##########################
#      ekroes            #
########################## P301  
SALES_VAT =0
BUY_VAT =0
BD_P377 = 0
F_RD_P_FPA('301', '331','SALES_VAT', .13)
F_RD_P_FPA('302', '332','SALES_VAT', .065)
F_RD_P_FPA('303', '333', 'SALES_VAT',.23)
F_RD_P_FPA('304', '334', 'SALES_VAT',.09)
F_RD_P_FPA('305', '335', 'SALES_VAT',.05)
F_RD_P_FPA('306', '336', 'SALES_VAT',.16)
F_RD_P_FPA_2('308','SALES_VAT')
F_RD_P_FPA_2('309','SALES_VAT')
F_RD_P_FPA_2('310','SALES_VAT')
F_RD_P_FPA_3('346')
#########################   
##### SUM EKROON        #
#########################
BD_P307 = NVLZ(BD_P301)+NVLZ(BD_P302)+NVLZ(BD_P303)+NVLZ(BD_P304)+NVLZ(BD_P305)+NVLZ(BD_P306)
BD_P311 = NVLZ(BD_P307) +NVLZ(BD_P308)+NVLZ(BD_P309)+NVLZ(BD_P310)
BD_P337 = NVLZ(BD_P331)+NVLZ(BD_P332)+NVLZ(BD_P333)+NVLZ(BD_P334)+NVLZ(BD_P335)+NVLZ(BD_P336)
##########################
#      EISROES            #
########################## 
F_RD_P_FPA('351', '371','BUY_VAT', .13)
F_RD_P_FPA('352', '372','BUY_VAT', .065)
F_RD_P_FPA('353', '373', 'BUY_VAT',.23)
F_RD_P_FPA('354', '374', 'BUY_VAT',.09)
F_RD_P_FPA('355', '375', 'BUY_VAT',.05)
F_RD_P_FPA('356', '376', 'BUY_VAT',.16)
F_RD_P_FPA_2('357', 'BD_P377')
#########################   
##### SUM EISROON        #
#########################
BD_P358 = NVLZ(BD_P351)+NVLZ(BD_P352)+NVLZ(BD_P353)+NVLZ(BD_P354)+NVLZ(BD_P355)+NVLZ(BD_P356)+NVLZ(BD_P357)
BD_P378 = NVLZ(BD_P371)+NVLZ(BD_P372)+NVLZ(BD_P373)+NVLZ(BD_P374)+NVLZ(BD_P375)+NVLZ(BD_P376)+NVLZ(BD_P377)
########################
### eidikoi logariasmoi#
########################
F_RD_P_FPA_3('341')
F_RD_P_FPA_3('342')
F_RD_P_FPA_3('343')
F_RD_P_FPA_3('344')
F_RD_P_FPA_3('345')
########################
BD_P400 = NVLZ(BD_P346)*0.05
RD_PREV_PER = sqlm(prev_per_sqlm%(I_last_rfch_id,'502'),num=sys.maxint)
Rec_Num = RD_PREV_PER.len()
PRV_VAT_VALUE =0
BD_P402=0
BD_P403=0
BD_P412=0
if Rec_Num !=0 :
    PRV_VAT_VALUE = NVLZ(RD_PREV_PER[1].RFCD_VALUE)
################################################################
#####   εαν 1η περιοδικη παιρνω τισ τιμεσ της εκκαθαριστικης
################################################################
if difmon == 1:
    RD_PREV_PER = sqlm(prev_per_sqlm%(I_liquidation_rfch_id,'802'),num=sys.maxint)
    Rec_Num = RD_PREV_PER.len()
    if Rec_Num !=0 :
        PRV_VAT_VALUE = NVLZ(RD_PREV_PER[1].RFCD_VALUE)
BD_P401=NVLZ(PRV_VAT_VALUE)
if NVLZ(BUY_VAT)>NVLZ(BD_P378):
    BD_P402 =NVLZ(BUY_VAT)-NVLZ(BD_P378)
else:
    BD_P412 = (NVLZ(BUY_VAT)-NVLZ(BD_P378)) *-1
##
###   εαν τροποποιητικη 
###
if I_modifiable == 1:
    RD_PREV_PER = sqlm(prev_per_sqlm%(I_last_rfch_id,'511'),num=sys.maxint)
    Rec_Num = RD_PREV_PER.len()
    if Rec_Num !=0 :
        BD_P403=NVLZ(RD_PREV_PER[1].RFCD_VALUE)
####
BD_P404=NVLZ(BD_P400)+NVLZ(BD_P401)+NVLZ(BD_P402)+NVLZ(BD_P403)
###
RD_PREV_PER = sqlm(prev_per_sqlm%(I_liquidation_rfch_id,'409'),num=sys.maxint)
Rec_Num = RD_PREV_PER.len()
BD_P411=0
if Rec_Num !=0 :
    ###NVLZ(RD_PREV_PER[1].RFCD_VALUE)
    BD_P411=NVLZ(RD_PREV_PER[1].RFCD_VALUE) * NVLZ(BD_P377)
##
###
if NVLZ(SALES_VAT)>NVLZ(BD_P337):
    BD_P412 =NVLZ(BD_P412) + NVLZ(SALES_VAT)-NVLZ(BD_P337)
RD_PREV_PER = sqlm(prev_per_sqlm%(I_last_rfch_id,'511'),num=sys.maxint)
Rec_Num = RD_PREV_PER.len()
if Rec_Num !=0 :
    if NVLZ(RD_PREV_PER[1].RFCD_VALUE) <=3:
        BD_P412 =NVLZ(BD_P412) +NVLZ(RD_PREV_PER[1].RFCD_VALUE)
BD_P413=NVLZ(BD_P411)+NVLZ(BD_P412)
###
BD_P420 = NVLZ(BD_P378)+NVLZ(BD_P404)-NVLZ(BD_P413)
BD_P511 =0
BD_P501 =0
if NVLZ(BD_P337) - NVLZ(BD_P420) >0 :
    BD_P511 =  NVLZ(BD_P337) - NVLZ(BD_P420)
else:
    BD_P501 =  (NVLZ(BD_P337) - NVLZ(BD_P420))*-1
BD_P512 =  NVLZ(BD_P511)*(I_increment_percent/100)
BD_P521=0
BD_P514 =0
BD_P522 =0
BD_P522 =0
if B_has_dosage is True:
    PAY_VALUE =  NVLZ(BD_P511)*0.40
    if NVLZ(I_payment_value) > NVLZ(PAY_VALUE):
        BD_P521 = NVLZ(I_payment_value)
    else:
        BD_P521 = NVLZ(PAY_VALUE)
    BD_P522 =  NVLZ(BD_P511) - NVLZ(BD_P521)
    BD_P514 =  NVLZ(BD_P522) *0.02
BD_P513 =  NVLZ(BD_P511) + NVLZ(BD_P512)+ NVLZ(BD_P514)
BD_P502 =  NVLZ(BD_P501)
