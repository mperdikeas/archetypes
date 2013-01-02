I_peri_id = 1
I_comp_id = 1
D_date_from = date(2012, 01, 1)
###I_year = D_date_from.year %100
###I_month= D_date_from.month
###I_day  = D_date_from.day
###D_date_to = date.today()
D_date_to =date(2012, 03, 31)
######I_rtfe_session_id = 10
####I_rtfh_id = 8
#####I_last_rfch_id =0
#####I_liquidation_rfch_id = 0
######I_increment_percent = 0
I_rerp_id = 0
I_modifiable = 0
B_has_dosage = False
I_payment_value = 0
I_external  = 4
I_month_intra  = 12
STR_exempt ='APPALAGH LOGV MH 2012'
B_reserved = True
I_modifiable = 1
########################################
################ rd company info #####################
comp_sqls = """
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
##############################
#  master e3  #
##############################
reem_sqlm = """
SELECT *
  FROM ER_RE_E3_MASTER
  WHERE PERI_ID =%d
"""
##############################
#  tax office  #
##############################
taof_sqlm = """
SELECT *
  FROM ER_TAX_OFFICES
  WHERE TAOF_ID =%d
"""
##############################
#  kaad   #
##############################
cooa_sqlm = """
SELECT *
  FROM ER_CODE_OF_ACTIVITIES
  WHERE COOA_ID =%d
"""
##############################
# EKDOTHENTA STOIXEIA       #
##############################
reei_sqlm = """
SELECT REEI_DESCRIPTION,
REEI_FROM_NUMBER,
REEI_TO_NUMBER
  FROM ER_RE_E3_INVOICES
  WHERE REEM_ID =%d 
  limit 10
"""
###########################
#  representative_info     #
############################
rerp_sqls = """
SELECT
* 
from v_er_re_representative_info
WHERE COMP_ID = %d
AND RERP_ID =%d  
"""
############################
#  REV_EXP VALUES          #
############################
p_rev_exp_sqlm = """
SELECT
SUM (retd_net_value) retd_net_value,
SUM (retd_vat_value) retd_vat_value,
SUM (retd_gross_value) retd_gross_value 
FROM v_e3_values 
WHERE PERI_ID = %d
AND reth_issue_date BETWEEN \'%s\' AND \'%s\'
AND ratf_code =\'%s\'   
"""
############################
#  mskk VALUES          #
############################
p_mskk_sqlm = """
SELECT rnpc_code,rnpc_percent,
sum( retd.retd_net_value ) AS retd_net_value,
sum( retd.retd_vat_value ) AS retd_vat_value,
sum( retd.retd_gross_value ) AS retd_gross_value
FROM 
er_re_tran_details retd,
er_re_net_profit_codes rnpc,
er_re_accounts reac,
er_re_tran_headers reth
WHERE reac.PERI_ID = %d
AND reth.peri_id = reac.peri_id
AND reth_issue_date BETWEEN \'%s\' AND \'%s\'
AND retd.reth_id = reth.reth_id
AND retd.reac_id = reac.reac_id 
AND reac.rnpc_id is not null
and rnpc.rnpc_id = reac.rnpc_id
GROUP BY rnpc_code,rnpc_percent
limit  6
"""
#############################
# er_re_tax_reforms               #
##############################
retf_sqlm = """
select sum(REFORM_VALUE) from
(select retf_id,retf_code,
CASE
WHEN rtrk_id = 9 THEN (
SELECT SUM (rtrt_value)
from er_re_tax_reforms_trans
WHERE PERI_ID = %d
AND retf_id = a.retf_id)
WHEN rtrk_id = 1 THEN (
SELECT SUM (rtrM_deductible_value)
FROM er_re_tax_reform_mobiles
WHERE PERI_ID = %d)
WHEN rtrk_id = 2 THEN (
SELECT SUM (rtrc_deductible_value)
FROM er_re_tax_reform_cars_trans
WHERE PERI_ID = %d)
end  REFORM_VALUE 
from er_re_tax_reforms a
WHERE retf_is_printed IS TRUE) as SU
"""
####################
def MAKE_123(SCODE,NVALUE):
    if NVALUE==1:
        globals()['STR_P' + str(SCODE)+str('a')] = 'X'
    elif NVALUE==2:
        globals()['STR_P' + str(SCODE)+str('b')] = 'X'
    elif NVALUE==3:
        globals()['STR_P' + str(SCODE)+str('c')] = 'X'
    elif NVALUE==4:
        globals()['STR_P' + str(SCODE)+str('d')] = 'X'
def F_RD_REV_EXP_WT(RATF_CODE,S_RATF_CODE):
    RD_REV_EXP = sqlm(p_rev_exp_sqlm%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    if RD_REV_EXP.len() != 0:
        globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_REV_EXP[1].RETD_NET_VALUE)
        u.println("************* S_RATF_CODE = %s" % S_RATF_CODE);
        ####globals()['BD_P'+str(S_RATF_CODE)] += NVLZ(RD_REV_EXP[1].RETD_NET_VALUE)
        accum(globals(),'BD_P'+str(S_RATF_CODE),NVLZ(RD_REV_EXP[1].RETD_NET_VALUE))
def F_RD_REV_EXP(RATF_CODE):
    RD_REV_EXP = sqlm(p_rev_exp_sqlm%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    if RD_REV_EXP.len() != 0:
        globals()['BD_P' + str(RATF_CODE)] = NVLZ(RD_REV_EXP[1].RETD_NET_VALUE)
def F_RD_REV_EXP_LV(RATF_CODE,SUM_RATF_CODE):
    RD_REV_EXP = sqlm(p_rev_exp_sqlm%(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d"),RATF_CODE))
    if RD_REV_EXP.len() != 0:
        globals()['LV_P' + str(RATF_CODE)] = NVLZ(RD_REV_EXP[1].RETD_NET_VALUE)
        accum(globals(),'LV_P' + SUM_RATF_CODE, NVLZ(RD_REV_EXP[1].RETD_NET_VALUE))
####################
#################################################################
########################
## stoixeia etaireias ##
########################
RD_COMP = sqls(comp_sqls%(I_comp_id))
RD_REEM = sqlm(reem_sqlm%(I_peri_id))
STR_P002 = D_date_from.strftime("%d/%m/%Y")
STR_P003 = D_date_to.strftime("%d/%m/%Y")
STR_P006 = RD_COMP.COMP_FILE_NUMBER
if (I_modifiable==1):
    STR_P008a = 'X'
else:
    STR_P008a = ' '
if (B_reserved):
    STR_P008b = 'X'
else:
    STR_P008b = ' '
STR_P005a = RD_COMP.TAOF_DESCRIPTION
STR_P005b = RD_COMP.TAOF_CODE
if RD_REEM.len() != 0:
    B1_DUMMY=RD_REEM[1].REEM_IS_PAR_7
    if B1_DUMMY is True:
        STR_P593      = 'X'
    else:
        STR_P593      = ' '
    B1_DUMMY      = RD_REEM[1].REEM_IS_OBSERVE
    if B1_DUMMY is True:
        STR_P594      = 'X'
    else:
        STR_P594      = ' '
    RD_TAOF = sqlm(taof_sqlm%(RD_REEM[1].TAOF_ID))
    if RD_TAOF.len() != 0:
        STR_P007a = RD_TAOF[1].TAOF_DESCRIPTION
        STR_P007b = RD_TAOF[1].TAOF_CODE
    MAKE_123('726',RD_REEM[1].REEO_ID)
    I_P730 =RD_REEM[1].REEM_CLOSED_YEAR
    RD_COOA = sqlm(cooa_sqlm%(RD_REEM[1].COOA_ID))
    if RD_COOA.len() != 0:
        STR_P761 = RD_COOA[1].COOA_DESCRIPTION
        STR_P761b = RD_COOA[1].COOA__CODE
if RD_COMP.BOCT_ID==2:    ##### EIDOS BIBLION
    STR_P019a='X'
elif RD_COMP.BOCT_ID == 3:
    STR_P019b      ='X'
STR_P018      = RD_COMP.COMP_TIN #### afm
STR_P705      = RD_COMP.COOA_CODE   ### kad
STR_P018a      = RD_COMP.COMP_DESCRIPTION
STR_P018c     = RD_COMP.COMP_FIRST_NAME
STR_P018d     = RD_COMP.COMP_FATHER_NAME
if RD_REEM.len() != 0:
##############################
#### stoixeia syzhgoy    #####
##############################    
    STR_P041 = RD_REEM[1].REEM_HUSBAND_TIN
    STR_P041a = RD_REEM[1].REEM_HUSBAND_LAST_NAME
    STR_P041b = RD_REEM[1].REEM_HUSBAND_FIRST_NAME
    STR_P041c = RD_REEM[1].REEM_HUSBAND_FATHER_NAME
#################
### pinakas B ###
#################
    RD_REEI = sqlm(reei_sqlm%(RD_REEM[1].REEM_ID))
    for i in range(1,RD_REEI.len()+1):
        for j in range(1,4):
            varname = 'STR_PB' + str(i)+"_"+str(j)
            value = AnyToString(RD_REEI[i][j])
            #####value = unicode(RD_REEI[i][j], 'iso8859-7')
            globals()[varname]=value
##################
### pinakas G ###
#################
if RD_REEM.len() != 0:
    I_P061 =  RD_REEM[1].REEM_SUBSTORES
    I_P064 =  RD_REEM[1].REEM_WAREHOUSE
    I_P067 =  RD_REEM[1].REEM_SHOW
    I_P070 =  RD_REEM[1].REEM_WORKSITE
    I_P073 =  RD_REEM[1].REEM_OTHER_SITES
    B1_DUMMY = RD_REEM[1].REEM_HAS_INTERNET_SALES
    if B1_DUMMY is True:
        STR_P076      = 'X'
    I_P062 =  RD_REEM[1].REEM_TAX_WAREHOUSE
    I_P065 =  RD_REEM[1].REEM_PERMANENT_STAFF
    I_P068 =  RD_REEM[1].REEM_SEASONAL_STAFF
    I_P071 =  RD_REEM[1].REEM_UNAUDITED_YEARS
    I_P074 =  RD_REEM[1].REEM_RELATED_COMPANIES
    B1_DUMMY = RD_REEM[1].REEM_HAS_RENDERING_SERVICE
    if B1_DUMMY is True:
        STR_P077      = 'X'
    MAKE_123('063',RD_REEM[1].REEM_WRH_BOOK_RECK_ID)
    MAKE_123('066',RD_REEM[1].REEM_MPS_RECK_ID)
    MAKE_123('069',RD_REEM[1].REEM_COST_ACCOUNT_RECK_ID)
    MAKE_123('072',RD_REEM[1].REEM_ELECTRONIC_RECK_ID)
    MAKE_123('075',RD_REEM[1].REEM_INVENTORY_RECK_ID)
    MAKE_123('078',RD_REEM[1].REEM_INTERNATIONAL_RECK_ID)
####################
## pinakas D       ##
#####################
if NVLZ(I_rerp_id) != 0:
    RD_RERP = sqls(rerp_sqls %(I_comp_id,I_rerp_id))
    MAKE_123('736',RD_RERP.RERK_ID)
    STR_P736a = RD_RERP.RERP_LAST_NAME
    STR_P736b = RD_RERP.RERP_LAST_NAME2   
    STR_P736c = RD_RERP.RERP_FIRST_NAME
    STR_P736d = RD_RERP.RERP_FATHER_NAME
    STR_P741 = RD_RERP.RERP_TIN
    STR_P741a = RD_RERP.TAOF_CODE
    STR_P741b = RD_RERP.IDTY_CODE
    STR_P741c = RD_RERP.RERP_IDENTITY_NUMBER
    MAKE_123('741c',RD_RERP.RESK_ID)
    STR_P741d = RD_RERP.RERP_STREET+" "+RD_RERP.RERP_NUMBER
    STR_P741e = RD_RERP.RERP_CITY
    STR_P741f = RD_RERP.RERP_ZIP_CODE
    STR_P061j = RD_RERP.TAOF_DESCRIPTION
if RD_REEM.len() != 0:
    MAKE_123('750',RD_REEM[1].REDY_ID)
#################
### pinakas E ###
#################
    BD_P681= RD_REEM[1].REEM_SUBSIDY_VALUE
    BD_P904= RD_REEM[1].REEM_GRANT_VALUE
    STR_P671a= RD_REEM[1].REEM_P671A
    STR_P671b= RD_REEM[1].REEM_P671B
    STR_P671c= RD_REEM[1].REEM_P671C
    STR_P671d= RD_REEM[1].REEM_P671D
    STR_P671= RD_REEM[1].REEM_P671
    BD_P672= RD_REEM[1].REEM_P672
    STR_P673a= RD_REEM[1].REEM_P673A
    STR_P673b= RD_REEM[1].REEM_P673B
    STR_P673c= RD_REEM[1].REEM_P673C
    STR_P673d= RD_REEM[1].REEM_P673D
    STR_P673= RD_REEM[1].REEM_P673
    BD_P674= RD_REEM[1].REEM_P674
    STR_P675a= RD_REEM[1].REEM_P675A
    STR_P675b= RD_REEM[1].REEM_P675B
    STR_P675c= RD_REEM[1].REEM_P675C
    STR_P675d= RD_REEM[1].REEM_P675D
    STR_P675= RD_REEM[1].REEM_P675
    BD_P676= RD_REEM[1].REEM_P676
    STR_P677a= RD_REEM[1].REEM_P677A
    STR_P677b= RD_REEM[1].REEM_P677B
    STR_P677c= RD_REEM[1].REEM_P677C
    STR_P677d= RD_REEM[1].REEM_P677D
    STR_P677= RD_REEM[1].REEM_P677
    BD_P678= RD_REEM[1].REEM_P678
    STR_P679a= RD_REEM[1].REEM_P679A
    STR_P679b= RD_REEM[1].REEM_P679B
    STR_P679c= RD_REEM[1].REEM_P679C
    STR_P679d= RD_REEM[1].REEM_P679D
    STR_P679= RD_REEM[1].REEM_P679
    BD_P680= RD_REEM[1].REEM_P680
    STR_P906a= RD_REEM[1].REEM_P906A
    STR_P906b= RD_REEM[1].REEM_P906B
    STR_P906c= RD_REEM[1].REEM_P906C
    STR_P906d= RD_REEM[1].REEM_P906D
    STR_P906= RD_REEM[1].REEM_P906
##############################
### pinakas st aagores    ###
##############################	
F_RD_REV_EXP_WT('231','251')
F_RD_REV_EXP_WT('235','251')
F_RD_REV_EXP_WT('239','251')
F_RD_REV_EXP_WT('243','251')
F_RD_REV_EXP_WT('247','251')
F_RD_REV_EXP('811')
F_RD_REV_EXP_WT('232','252')
F_RD_REV_EXP_WT('236','252')
F_RD_REV_EXP_WT('240','252')
F_RD_REV_EXP_WT('244','252')
F_RD_REV_EXP_WT('248','252')
F_RD_REV_EXP('812')
##############################
### pinakas st b apografes ###
##############################
if RD_REEM.len() != 0:
    BD_P256= RD_REEM[1].REEM_GOODS_START_VALUE
    BD_P257= RD_REEM[1].REEM_GOODS_END_VALUE
    BD_P259= RD_REEM[1].REEM_PRODUCTS_START_VALUE
    BD_P260= RD_REEM[1].REEM_PRODUCTS_END_VALUE
    BD_P261= RD_REEM[1].REEM_MATERIALS_START_VALUE
    BD_P262= RD_REEM[1].REEM_MATERIALS_END_VALUE
    BD_P264= RD_REEM[1].REEM_PACKAGING_START_VALUE
    BD_P265= RD_REEM[1].REEM_PACKAGING_END_VALUE
    BD_P267= RD_REEM[1].REEM_INCOMPLETE_START_VALUE
    BD_P268= RD_REEM[1].REEM_INCOMPLETE_END_VALUE
    BD_P270= RD_REEM[1].REEM_RESIDUE_START_VALUE
    BD_P271= RD_REEM[1].REEM_RESIDUE_END_VALUE
    BD_P520 =NVLZ(BD_P256)+NVLZ(BD_P259)+NVLZ(BD_P261)+NVLZ(BD_P264)+NVLZ(BD_P267)+NVLZ(BD_P270)
    BD_P521 =NVLZ(BD_P257)+NVLZ(BD_P260)+NVLZ(BD_P262)+NVLZ(BD_P265)+NVLZ(BD_P268)+NVLZ(BD_P271)
#########################################
### pinakas st  G AKATHARISTA EMPORIAS###
#########################################
F_RD_REV_EXP_WT('263','540')
F_RD_REV_EXP_WT('266','540')
F_RD_REV_EXP_WT('269','540')
F_RD_REV_EXP_WT('272','540')
F_RD_REV_EXP_WT('273','540')
#########################################
### pinakas st  E AKATHARISTA PAROXHS ###
#########################################
F_RD_REV_EXP_WT('279','547')
F_RD_REV_EXP_WT('276','547')
F_RD_REV_EXP_WT('519','547')
F_RD_REV_EXP_WT('282','547')
F_RD_REV_EXP_WT('273','547')
#########################################
### pinakas st  z AKATHARISTA ELEYTHER ###
#########################################
F_RD_REV_EXP_WT('274','283')
F_RD_REV_EXP_WT('277','283')
F_RD_REV_EXP_WT('280','283')
F_RD_REV_EXP_WT('275','283')
F_RD_REV_EXP_WT('278','283')
#########################################
### pinakas st  D DAPANES            ###
#########################################
F_RD_REV_EXP_LV('522','sum522')
F_RD_REV_EXP_LV('523','sum522')
F_RD_REV_EXP_LV('524','sum522')
F_RD_REV_EXP_LV('525','sum525')
F_RD_REV_EXP_LV('526','sum525')
F_RD_REV_EXP_LV('527','sum525')
F_RD_REV_EXP_LV('528','sum528')
F_RD_REV_EXP_LV('529','sum528')
F_RD_REV_EXP_LV('530','sum528')
F_RD_REV_EXP_LV('531','sum531')
F_RD_REV_EXP_LV('532','sum531')
F_RD_REV_EXP_LV('533','sum531')
F_RD_REV_EXP_LV('534','sum534')
F_RD_REV_EXP_LV('535','sum534')
F_RD_REV_EXP_LV('536','sum534')
F_RD_REV_EXP_LV('537','sum537')
F_RD_REV_EXP_LV('538','sum537')
F_RD_REV_EXP_LV('539','sum537')
F_RD_REV_EXP_LV('541','sum541')
F_RD_REV_EXP_LV('542','sum541')
F_RD_REV_EXP_LV('543','sum541')
##########
#### pososta epi ton poliseon
######
if RD_REEM.len() != 0:
    PER_EMP = NVLZ(BD_P540)*100/(NVLZ(BD_P540)+NVLZ(BD_P547)+NVLZ(BD_P283))
    PER_PAR = NVLZ(BD_P547)*100/(NVLZ(BD_P540)+NVLZ(BD_P547)+NVLZ(BD_P283))
    PER_ELE = 100-(PER_EMP+PER_PAR) ######   NVLZ(BD_P283)*100/(NVLZ(BD_P540)+NVLZ(BD_P547)+NVLZ(BD_P283))
    if RD_REEM[1].REEM_P522_HAS_SHARING is True:
        BD_P522 = NVLZ(LV_Psum522)*PER_EMP/100
        BD_P523 = NVLZ(LV_Psum522)*PER_PAR/100
        BD_P524 = NVLZ(LV_Psum522)*PER_ELE/100
    else:
        BD_P522 =LV_P522
        BD_P523 =LV_P523
        BD_P524 =LV_P524
    if RD_REEM[1].REEM_P525_HAS_SHARING is True:
        BD_P525 = NVLZ(LV_Psum525)*PER_EMP/100
        BD_P526 = NVLZ(LV_Psum525)*PER_PAR/100
        BD_P527 = NVLZ(LV_Psum525)*PER_ELE/100
    else:
        BD_P525 =LV_P525
        BD_P526 =LV_P526
        BD_P527 =LV_P527
    if RD_REEM[1].REEM_P528_HAS_SHARING is True:
        BD_P528 = NVLZ(LV_Psum528)*PER_EMP/100
        BD_P529 = NVLZ(LV_Psum528)*PER_PAR/100
        BD_P530 = NVLZ(LV_Psum528)*PER_ELE/100
    else:
        BD_P528 =LV_P528
        BD_P529 =LV_P529
        BD_P530 =LV_P530
    if RD_REEM[1].REEM_P531_HAS_SHARING is True:
        BD_P531 = NVLZ(LV_Psum531)*PER_EMP/100
        BD_P532 = NVLZ(LV_Psum531)*PER_PAR/100
        BD_P533 = NVLZ(LV_Psum531)*PER_ELE/100
    else:
        BD_P531 =LV_P531
        BD_P532 =LV_P532
        BD_P533 =LV_P533
    if RD_REEM[1].REEM_P534_HAS_SHARING is True:
        BD_P534 = NVLZ(LV_Psum534)*PER_EMP/100
        BD_P535 = NVLZ(LV_Psum534)*PER_PAR/100
        BD_P536 = NVLZ(LV_Psum534)*PER_ELE/100
    else:
        BD_P534 =LV_P534
        BD_P535 =LV_P535
        BD_P536 =LV_P536
    if RD_REEM[1].REEM_P537_HAS_SHARING is True:
        BD_P537 = NVLZ(LV_Psum537)*PER_EMP/100
        BD_P538 = NVLZ(LV_Psum537)*PER_PAR/100
        BD_P539 = NVLZ(LV_Psum537)*PER_ELE/100
    else:
        BD_P537 =LV_P537
        BD_P538 =LV_P538
        BD_P539 =LV_P539
    if RD_REEM[1].REEM_P541_HAS_SHARING is True:
        BD_P541 = NVLZ(LV_Psum541)*PER_EMP/100
        BD_P542 = NVLZ(LV_Psum541)*PER_PAR/100
        BD_P543 = NVLZ(LV_Psum541)*PER_ELE/100
    else:
        BD_P541 =LV_P541
        BD_P542 =LV_P542
        BD_P543 =LV_P543
else:
    BD_P522 =LV_P522
    BD_P523 =LV_P523
    BD_P524 =LV_P524
    BD_P525 =LV_P525
    BD_P526 =LV_P526
    BD_P527 =LV_P527
    BD_P528 =LV_P528
    BD_P529 =LV_P529
    BD_P530 =LV_P530
    BD_P531 =LV_P531
    BD_P532 =LV_P532
    BD_P533 =LV_P533
    BD_P534 =LV_P534
    BD_P535 =LV_P535
    BD_P536 =LV_P536
    BD_P537 =LV_P537
    BD_P538 =LV_P538
    BD_P539 =LV_P539
    BD_P541 =LV_P541
    BD_P542 =LV_P542
    BD_P543 =LV_P543
BD_P544=NVLZ(BD_P522)+NVLZ(BD_P525)+NVLZ(BD_P528)+NVLZ(BD_P531)+NVLZ(BD_P534)+NVLZ(BD_P537)+NVLZ(BD_P541)
BD_P545=NVLZ(BD_P523)+NVLZ(BD_P526)+NVLZ(BD_P529)+NVLZ(BD_P532)+NVLZ(BD_P535)+NVLZ(BD_P538)+NVLZ(BD_P542)
BD_P546=NVLZ(BD_P524)+NVLZ(BD_P527)+NVLZ(BD_P530)+NVLZ(BD_P533)+NVLZ(BD_P536)+NVLZ(BD_P539)+NVLZ(BD_P543)
#########################################
### pinakas st  st        mskk       ###
#########################################
RD_MSKK = sqlm(p_mskk_sqlm %(I_peri_id,D_date_from.strftime("%Y-%m-%d"),D_date_to.strftime("%Y-%m-%d")))
BD_P344 = 0
if RD_MSKK.len() != 0:
    A=302
    B=304
    C=359
    for i in range(1,RD_MSKK.len()+1):
        varname_a = 'STR_P' + str(A)
        value_a = AnyToString(RD_MSKK[i][1])
        globals()[varname_a]=value_a
        varname_b = 'BD_P' + str(B)
        value_b = RD_MSKK[i][3]
        globals()[varname_b]=value_b
        varname_c = 'BD_P' + str(C)
        value_c = RD_MSKK[i][2]
        globals()[varname_c]=value_c
        A=A+6
        B=B+6
        C=C+1
        BD_P344 = BD_P344+value_b
#########################################
### pinakas st  h       pros log kerd ###
#########################################
BD_P548=BD_P540
BD_P549=BD_P547
BD_P550=BD_P283
BD_P551=BD_P548+BD_P549+BD_P550
BD_P552=BD_P251+BD_P252+BD_P520-BD_P521
BD_P555=BD_P552
BD_P556=BD_P544
BD_P557=BD_P545
BD_P558=BD_P546
BD_P559=BD_P556+BD_P557+BD_P558
BD_P560=BD_P548-BD_P552-BD_P556
BD_P561=BD_P549-BD_P557 ### -BD_P553
BD_P562=BD_P550-BD_P558   ###-BD_P554
BD_P563=BD_P560+BD_P561+BD_P562
RD_RETF_PARAM=retf_sqlm%(I_peri_id,I_peri_id,I_peri_id)
RD_RETF = sqlm(RD_RETF_PARAM)
if RD_RETF.len() != 0:
    RETF_VALUE =RD_RETF[1][1]
else:
    RETF_VALUE =0
BD_P564 = NVLZ(RETF_VALUE)*PER_EMP/100
BD_P565 = NVLZ(RETF_VALUE)*PER_PAR/100
BD_P566 = NVLZ(RETF_VALUE)*PER_ELE/100
BD_P567 = BD_P564 + BD_P565 + BD_P566
BD_P568 = BD_P560+BD_P564
BD_P569 = BD_P561+BD_P565
BD_P570 = BD_P562+BD_P566
BD_P571 = BD_P568+BD_P569+BD_P570
