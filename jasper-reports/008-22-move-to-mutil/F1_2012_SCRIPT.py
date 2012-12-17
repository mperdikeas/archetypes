############## PARAMETROI     
I_peri_id = 1
I_comp_id = 1
D_date_from = date(2012, 1, 1)
D_date_to =date(2012, 12, 31)
I_rtfe_session_id = 10
I_rtfh_id = 8
I_liquidation_rfch_id = 0
I_increment_percent = 0
I_rerp_id = 1
I_payment_value = 0
I_return_vat_value = 0   ###### forma
B_is_for_return  = True  ###### forma
I_return_reason = 1 ###### forma
I_modifiable = 0
B_reserved = True
I_mandatory_book =1  ###### forma
I_vat_type  =1       ###### forma
I_mandatory   =1        ##### forma
BD_P422_1      =1.00
BD_P422_3      =1.00
BD_P422_4      =1.00
BD_P422_5      =1.00
########################################
################ rd company info #####################
comp_sqls = """
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
############################
#  REV_EXP VALUES          #
############################
p_pfa_sqls = """
SELECT
SUM (retd_net_value) retd_net_value,
SUM (retd_vat_value) retd_vat_value,
SUM (retd_gross_value) retd_gross_value 
FROM v_e_fpa_values 
WHERE PERI_ID = %d
AND reth_issue_date BETWEEN \'%s\' AND \'%s\'
AND ratf_code =\'%s\'   
"""
############################
#  representative_info     #
############################
rerp_sqls = """
SELECT
* 
from v_er_re_representative_info
WHERE COMP_ID = %d
AND RERP_ID =%d  
"""
##############################
#  settlement_assets_values  #
##############################
resa_sqlm = """
SELECT *
  FROM er_re_settlement_assets
  WHERE PERI_ID =%d
  and resa_code=\'%s\' 
"""
##############################
#  tax info                  #
##############################
rtfi_sqlm = """
SELECT *
  FROM er_re_tax_form_info
  WHERE PERI_ID =%d
"""
################## 
def F_RD_P_FPA(RATF_CODE, RATF_CODE2,pref, coefficient):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
    globals()['BD_P' + str(RATF_CODE2)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)*coefficient
    globals()[pref] += NVLZ(RD_P_FPA.RETD_VAT_VALUE)
    if pref=='SALES_VAT':
        globals()['BD_P607'] += NVLZ(RD_P_FPA.RETD_NET_VALUE)
        globals()['BD_P637'] += NVLZ(RD_P_FPA.RETD_NET_VALUE)*coefficient
    elif pref=='BUY_VAT':
        globals()['BD_P664'] += NVLZ(RD_P_FPA.RETD_NET_VALUE)
        globals()['BD_P684'] += NVLZ(RD_P_FPA.RETD_NET_VALUE)*coefficient
def F_RD_P_FPA_2(RATF_CODE,pref):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
    globals()[pref] += NVLZ(RD_P_FPA.RETD_VAT_VALUE)
def F_RD_P_FPA_3(RATF_CODE):
    RD_P_FPA = sqls(p_pfa_sqls%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_P_FPA.RETD_NET_VALUE)
def F_RD_RESA(RESA_CODE):
    RD_RESA = sqlm(resa_sqlm%(I_peri_id,RESA_CODE))
    Rec_Num = RD_RESA.len()
    if Rec_Num !=0 :
        globals()['BD_P' + str(RESA_CODE)+'a'] = NVLZ(RD_RESA[1].RESA_UNIQUE_VALUE)
        globals()['B_P' + str(RESA_CODE)+'b'] = RD_RESA[1].RESA_PRORATA_IS_UNIQUE
        globals()['BD_P' + str(RESA_CODE)+'c'] = NVLZ(RD_RESA[1].RESA_BUY_VAT_VALUE)
        globals()['BD_P' + str(RESA_CODE)+'d'] = NVLZ(RD_RESA[1].RESA_PRORATA_CHANGE_VALUE)
        globals()['BD_P' + str(RESA_CODE)+'e'] = NVLZ(RD_RESA[1].RESA_BUY_VAT_VALUE_2)
        globals()['BD_P' + str(RESA_CODE)+'f'] = NVLZ(RD_RESA[1].RESA_PRORATA_CALCULATIONS)
        globals()['BD_P' + str(RESA_CODE)+'g'] = NVLZ(RD_RESA[1].RESA_NO_VAT_VALUE)
        globals()['BD_P' + str(RESA_CODE)+'h'] = NVLZ(RD_RESA[1].RESA_PREVIOUS_YEAR_VALUE)
        globals()['BD_P' + str(RESA_CODE)+'i'] = NVLZ(RD_RESA[1].RESA_VAT_NO_TAX_VALUE)
        globals()['BD_P506c'] += NVLZ(RD_RESA[1].RESA_BUY_VAT_VALUE)
        globals()['BD_P506d'] += NVLZ(RD_RESA[1].RESA_PRORATA_CHANGE_VALUE)
        globals()['BD_P506e'] += NVLZ(RD_RESA[1].RESA_BUY_VAT_VALUE_2)
        globals()['BD_P506f'] += NVLZ(RD_RESA[1].RESA_PRORATA_CALCULATIONS)
        globals()['BD_P506g'] += NVLZ(RD_RESA[1].RESA_NO_VAT_VALUE)
        globals()['BD_P506h'] += NVLZ(RD_RESA[1].RESA_PREVIOUS_YEAR_VALUE)
        globals()['BD_P506i'] += NVLZ(RD_RESA[1].RESA_VAT_NO_TAX_VALUE)
    else:
        globals()['BD_P' + str(RESA_CODE)+'a'] = 0
        globals()['B_P' + str(RESA_CODE)+'b'] = 0
        globals()['BD_P' + str(RESA_CODE)+'c'] = 0
        globals()['BD_P' + str(RESA_CODE)+'d'] = 0
        globals()['BD_P' + str(RESA_CODE)+'e'] = 0
        globals()['BD_P' + str(RESA_CODE)+'f'] = 0
        globals()['BD_P' + str(RESA_CODE)+'g'] = 0
        globals()['BD_P' + str(RESA_CODE)+'h'] = 0
        globals()['BD_P' + str(RESA_CODE)+'i'] = 0
def MAKE_STR(POS):
    globals()['STR_P' + str(POS)] = 'X'
################################################################################
##############################
## RD PREVIOUS VAT PERIOD  ###
##############################
prev_per_sqlm = 'SELECT rfcd_value  FROM er_re_form_considered_details WHERE rfch_id=%d and rfcd_rtfd_code =\'%s\''
###
###################################################AND ratf_code =\'%s\'
RD_COMP = sqls(comp_sqls%(I_comp_id))
STR_P001a = RD_COMP.TAOF_DESCRIPTION
STR_P001b =     RD_COMP.TAOF_CODE
STR_P002a_d   = D_date_from.strftime("%d")
STR_P002a_m   = D_date_from.strftime("%m")
STR_P002a_y   = D_date_from.strftime("%y")
STR_P002b_d   = D_date_to.strftime("%d")
STR_P002b_m   = D_date_to.strftime("%m")
STR_P002b_y   = D_date_to.strftime("%y")
STR_P004      = D_date_from.strftime("%Y") 
STR_P006d     = date.today().strftime("%d")
STR_P006m     = date.today().strftime("%m")
STR_P006y     = date.today().strftime("%y")
STR_P008      = RD_COMP.COMP_FILE_NUMBER
if (I_modifiable==1):
    STR_P009 = 'X'
else:
    STR_P009 = ' '
if (B_reserved):
    STR_P010 = 'X'
else:
    STR_P010 = ' '
STR_P021      = RD_COMP.COMP_DESCRIPTION
STR_P022      = RD_COMP.COMP_FIRST_NAME
STR_P023      = RD_COMP.COMP_FATHER_NAME
STR_P024      = RD_COMP.COMP_TITLE
STR_P025      = RD_COMP.COMP_STREET+' '+ RD_COMP.COMP_NUMBER
STR_P026      = RD_COMP.COMP_CITY
STR_P027a     = RD_COMP.COMP_ZIP_CODE
STR_P027b     = RD_COMP.COMP_PHONE1
STR_P028a     = RD_COMP.COMP_SUBJECT
STR_P028b     = RD_COMP.COOA_CODE
STR_P031      = RD_COMP.COMP_TIN
STR_P032      = RD_COMP.COMP_IDENTITY_NUMBER
B1_DUNMMY      = RD_COMP.COMP_HAS_INTRA_TRANS
if B1_DUNMMY is True:
    STR_P033a      = 'X'
else:
    STR_P033b      = ' '
B1_DUNMMY      = RD_COMP.COMP_HAS_REMOTE_SALES
if B1_DUNMMY is True:
    STR_P034      = 'X'
else:
    STR_P034a      = ' '
if RD_COMP.BOCT_ID==2:
    STR_P041a='X'
elif RD_COMP.BOCT_ID == 3:
    STR_P041b      ='X'
if I_mandatory_book == 1:
    STR_P042a      ='X'
elif I_mandatory_book == 2:
    STR_P042b      ='X'
elif I_mandatory_book == 3:
    STR_P042c      ='X'
STR_P043      ='eee'
STR_P044      ='X'.rjust(I_mandatory)
#####################
## pinakas B       ##
#####################
if NVLZ(I_rerp_id) != 0:
    RD_RERP = sqls(rerp_sqls %(I_comp_id,I_rerp_id))
    if RD_RERP.RERK_ID ==1:
        STR_P061_1='X'
    elif RD_RERP.RERK_ID ==2:
        STR_P061_2='X'
    elif RD_RERP.RERK_ID ==3:
        STR_P061_3='X'
    STR_P061a = RD_RERP.RERP_LAST_NAME
    STR_P061b = RD_RERP.RERP_TIN
    STR_P061c = RD_RERP.RERP_FIRST_NAME
    STR_P061d = RD_RERP.RERP_FATHER_NAME
    STR_P061e1 = RD_RERP.IDTY_CODE
    STR_P061e = RD_RERP.RERP_IDENTITY_NUMBER
    STR_P061f = RD_RERP.RERP_STREET+" "+RD_RERP.RERP_NUMBER
    STR_P061g = RD_RERP.RERP_CITY
    STR_P061h = RD_RERP.RERP_ZIP_CODE
    STR_P061j = RD_RERP.TAOF_DESCRIPTION
    STR_P061k = RD_RERP.TAOF_CODE

####################
## pinakas H       ##
#####################
BD_P506c=0
BD_P506d=0
BD_P506e=0
BD_P506f=0
BD_P506g=0
BD_P506h=0
BD_P506i=0
BD_P702=0
BD_P706=0
F_RD_RESA('501')
F_RD_RESA('502')
F_RD_RESA('503')
F_RD_RESA('504')
F_RD_RESA('505')
BD_P507 =NVLZ(BD_P506d)+NVLZ(BD_P506f)-NVLZ(BD_P506h)+NVLZ(BD_P506i)
if BD_P507 >30:
    BD_P702 = BD_P507
else:
    BD_P706 = BD_P507
####################
## pinakas Θ      ##
#####################
RD_RTFI_PARAM=rtfi_sqlm%(I_peri_id)
RD_RTFI = sqlm(RD_RTFI_PARAM)
materialize('BD_P', RD_RTFI, 'RTFI_CODE', 'RTFI_VALUE', NVLZ)
###################################
#    pinakas g  ekroes            #
###################################
SALES_VAT =0
BUY_VAT =0
BD_P607 = 0
BD_P637 = 0
F_RD_P_FPA('101', '131','SALES_VAT', .09)
F_RD_P_FPA('102', '132','SALES_VAT', .045)
F_RD_P_FPA('103', '133', 'SALES_VAT',.19)
F_RD_P_FPA('201', '231', 'SALES_VAT',.10)
F_RD_P_FPA('202', '232', 'SALES_VAT',.05)
F_RD_P_FPA('203', '233', 'SALES_VAT',.21)
F_RD_P_FPA('601', '631','SALES_VAT', .11)
F_RD_P_FPA('602', '632','SALES_VAT', .055)
F_RD_P_FPA('603', '633', 'SALES_VAT',.23)
F_RD_P_FPA('301', '331', 'SALES_VAT',.13)
F_RD_P_FPA('302', '332', 'SALES_VAT',.065)
F_RD_P_FPA('104', '134','SALES_VAT', .06)
F_RD_P_FPA('105', '135','SALES_VAT', .03)
F_RD_P_FPA('106', '136', 'SALES_VAT',.13)
F_RD_P_FPA('204', '234', 'SALES_VAT',.07)
F_RD_P_FPA('205', '235', 'SALES_VAT',.04)
F_RD_P_FPA('206', '236', 'SALES_VAT',.15)
F_RD_P_FPA('604', '634','SALES_VAT', .08)
F_RD_P_FPA('605', '635','SALES_VAT', .04)
F_RD_P_FPA('606', '636', 'SALES_VAT',.16)
F_RD_P_FPA('304', '334','SALES_VAT', .09)
F_RD_P_FPA('305', '335','SALES_VAT', .05)
F_RD_P_FPA_2('608','SALES_VAT')
F_RD_P_FPA_2('609','SALES_VAT')
F_RD_P_FPA_2('610','SALES_VAT')
F_RD_P_FPA_2('611','SALES_VAT')
F_RD_P_FPA_3('613')
#########################   
##### SUM EKROON        #
#########################
BD_P612 = NVLZ(BD_P607) +NVLZ(BD_P608)+NVLZ(BD_P609)+NVLZ(BD_P610)+NVLZ(BD_P611)
BD_P614 = NVLZ(BD_P612)-NVLZ(BD_P613)
##########################
#      EISROES            #
########################## P351
BD_P664 = 0
BD_P684 = 0
BD_P683 = 0
F_RD_P_FPA('151', '171','BUY_VAT', .09)
F_RD_P_FPA('152', '172','BUY_VAT', .045)
F_RD_P_FPA('153', '173', 'BUY_VAT',.19)
F_RD_P_FPA('251', '271', 'BUY_VAT',.10)
F_RD_P_FPA('252', '272', 'BUY_VAT',.05)
F_RD_P_FPA('253', '273', 'BUY_VAT',.21)
F_RD_P_FPA('651', '671','BUY_VAT', .11)
F_RD_P_FPA('652', '672','BUY_VAT', .055)
F_RD_P_FPA('653', '673', 'BUY_VAT',.23)
F_RD_P_FPA('361', '371', 'BUY_VAT',.13)
F_RD_P_FPA('362', '372', 'BUY_VAT',.065)
F_RD_P_FPA('157', '177','BUY_VAT', .06)
F_RD_P_FPA('158', '178','BUY_VAT', .03)
F_RD_P_FPA('159', '179', 'BUY_VAT',.13)
F_RD_P_FPA('257', '277', 'BUY_VAT',.07)
F_RD_P_FPA('258', '278', 'BUY_VAT',.04)
F_RD_P_FPA('259', '279', 'BUY_VAT',.15)
F_RD_P_FPA('657', '677','BUY_VAT', .08)
F_RD_P_FPA('658', '678','BUY_VAT', .04)
F_RD_P_FPA('659', '679', 'BUY_VAT',.16)
F_RD_P_FPA('367', '377','BUY_VAT', .09)
F_RD_P_FPA('368', '378','BUY_VAT', .05)
F_RD_P_FPA_2('663', 'BD_P683')
F_RD_P_FPA_3('665')
F_RD_P_FPA_3('666')
#########################   
##### SUM EISROON        #
#########################
BD_P664 = NVLZ(BD_P664)+NVLZ(BD_P663)
BD_P684 = NVLZ(BD_P684)+NVLZ(BD_P683)
BD_P667 = NVLZ(BD_P664)+NVLZ(BD_P665)+NVLZ(BD_P666)
########################
### eidikoi logariasmoi#
########################
F_RD_P_FPA_3('641')
F_RD_P_FPA_3('642')
F_RD_P_FPA_3('644')
F_RD_P_FPA_3('645')
F_RD_P_FPA_3('646')
BD_P647=NVLZ(BD_P641)+NVLZ(BD_P642)+NVLZ(BD_P644)+NVLZ(BD_P645)+NVLZ(BD_P646)
########################
F_RD_P_FPA_3('668')
BD_P688 = NVLZ(BD_P668)*0.05
####################
## pinakas z       ##
#####################
F_RD_P_FPA_3('403')
I_P408 =0
if NVLZ(BD_P403) !=0:
    BD_P401=NVLZ(BD_P607)-NVLZ(BD_P641)-NVLZ(BD_P645)
    F_RD_P_FPA_3('402')
    BD_P404=NVLZ(BD_P401)+NVLZ(BD_P402)+NVLZ(BD_P403)
    BD_P405=NVLZ(BD_P401)+NVLZ(BD_P402)
    BD_P406=NVLZ(BD_P404)
    BD_P407=NVLZ(BD_P405)*100/BD_P406
    I_P408=ceiling(BD_P407)
    BD_P409=100-I_P408
    BD_P421_1=NVLZ(BD_P683)
    BD_P421_2=NVLZ(BD_P409)
    BD_P421_6=NVLZ(BD_P421_1) *NVLZ(BD_P421_2)
    BD_P422_6=NVLZ(BD_P422_1)*NVLZ(BD_P422_3)*NVLZ(BD_P422_4)*NVLZ(BD_P422_5)
    BD_P423 =NVLZ(BD_P421_6)+NVLZ(BD_P422_6)
    if NVLZ(BD_P423)>30:
        BD_P706 =NVLZ(BD_P706)+NVLZ(BD_P423)
    if NVLZ(BD_P423)<0:
        BD_P702 =NVLZ(BD_P702)+abs(NVLZ(BD_P423))
######################################
BD_P703 =0
BD_P704 =0
PRV_VAT_VALUE = 0
BD_P701 = I_payment_value
RD_PREV_PER = sqlm(prev_per_sqlm%(I_liquidation_rfch_id,'802'),num=sys.maxint)
Rec_Num = RD_PREV_PER.len()
if Rec_Num !=0 :
    PRV_VAT_VALUE = NVLZ(RD_PREV_PER[1].RFCD_VALUE)
if NVLZ(BUY_VAT)>NVLZ(BD_P684):
    BD_P703 =NVLZ(BUY_VAT)-NVLZ(BD_P684)
else:
    BD_P708 = (NVLZ(BUY_VAT)-NVLZ(BD_P684)) *-1
BD_P703 = NVLZ(BD_P703)+NVLZ(PRV_VAT_VALUE)
##
###   εαν τροποποιητικη 
###
if I_modifiable == 1:
    RD_PREV_PER = sqlm(prev_per_sqlm%(I_last_rfch_id,'811'),num=sys.maxint)
    Rec_Num = RD_PREV_PER.len()
    if Rec_Num !=0 :
        BD_P704=NVLZ(RD_PREV_PER[1].RFCD_VALUE)
BD_P705=NVLZ(BD_P701)+NVLZ(BD_P702)+NVLZ(BD_P703)+NVLZ(BD_P704)
BD_P707 = I_return_vat_value
##
if NVLZ(SALES_VAT)>NVLZ(BD_P637):
    BD_P708 =NVLZ(BD_P708) + NVLZ(SALES_VAT)-NVLZ(BD_P637)
BD_P709 = NVLZ(BD_P706)+NVLZ(BD_P707)-NVLZ(BD_P708)
BD_P710 = NVLZ(BD_P684)+NVLZ(BD_P688)+NVLZ(BD_P705)-NVLZ(BD_P709)
####
##
BD_P811 =0
BD_P801 =0
if NVLZ(BD_P637) - NVLZ(BD_P710) >0 :
    BD_P811 =  NVLZ(BD_P637) - NVLZ(BD_P710)
    BD_P812 =  NVLZ(BD_P811)*(I_increment_percent/100)
    BD_P813 =  NVLZ(BD_P811) + NVLZ(BD_P812)
else:
    BD_P801 =  (NVLZ(BD_P637) - NVLZ(BD_P710))*-1
if NVLZ(BD_P801)!=0:
    if B_is_for_return is True :
        BD_P803 =BD_P801
        STR_P808 ='X'.rjust(I_return_reason)
    else:
        BD_P802 =BD_P801