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
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
#############################
# er_re_tax_reforms               #
##############################
retf_sqlm = """
select retf_id,retf_code,
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
WHERE retf_is_printed IS TRUE
ORDER BY RETF_ID
"""
##############################
# company_cars #
##############################
rtcc_sqlm = """
SELECT rtcc_leters,rtcc_number,rtcc_cc
  FROM er_re_tax_company_cars
  WHERE COMP_ID =%d 
  limit 10
"""
##############################
# company_cars    values    #
##############################
rtrc_sqlm = """
SELECT rtri_percent,
rtrc_expense_value,
rtrc_deductible_value
FROM er_re_tax_reform_cars_trans A,
er_re_tax_reform_cars_kinds B
WHERE PERI_ID = %d
AND A.RTRI_ID = %d
AND A.RTRI_ID = B.RTRI_ID
"""
##############################
# mobile   values    #
##############################
rtrm_sqlm = """
select rtrm_expense_value,
rtrm_mobile_number,
rtrm_employees,
rtrm_percent,
rtrm_deductible_value
from er_re_tax_reform_mobiles
WHERE PERI_ID = %d
"""
#############################
# FOROI        rtrd_id,to_char(rtrd_id,'000') rtrd_code,              #
##############################
rtrd_sqlm = """
select
(
SELECT SUM (rtru_result_value)
FROM er_re_tax_reform_due_trans
WHERE PERI_ID = %d
AND rtrd_id = A.rtrd_id
) result_value,
(
SELECT SUM (rtru_return_value)
FROM er_re_tax_reform_due_trans
WHERE PERI_ID =%d
AND rtrd_id = A.rtrd_id
) return_value
from er_re_tax_reform_due_taxes a
order by rtrd_id
"""
######################
## stoixeia etaireias#
######################
RD_COMP = sqls(comp_sqls%(I_comp_id))
STR_P1001 =RD_COMP.COMP_DESCRIPTION
STR_P1002 = RD_COMP.COMP_SUBJECT
STR_P1003 =RD_COMP.COMP_TIN
STR_P1004 =STR_RERP_FIRST_NAME +' '+STR_RERP_LAST_NAME +' ¡÷Ã :'+STR_RERP_TIN
###################################
### stoixeia dapanon          #####
###################################
RD_RETF_PARAM=retf_sqlm%(I_peri_id,I_peri_id,I_peri_id)
RD_RETF = sqlm(RD_RETF_PARAM)
materialize('BD_P', RD_RETF, 'RETF_CODE', 'REFORM_VALUE', NVLZ)
BD_PSUM=NVLZ(BD_P1)+NVLZ(BD_P2)+NVLZ(BD_P3)+NVLZ(BD_P4)+NVLZ(BD_P5)+NVLZ(BD_P6)+NVLZ(BD_P7)+NVLZ(BD_P8)+NVLZ(BD_P9)+NVLZ(BD_P10)+NVLZ(BD_P11)+NVLZ(BD_P12)+NVLZ(BD_P13)+NVLZ(BD_P14)+NVLZ(BD_P15)+NVLZ(BD_P16)+NVLZ(BD_P17)+NVLZ(BD_P18)
#################################
## aytokinita                  #
################################
RD_RTCC = sqlm(rtcc_sqlm%(I_comp_id))
for i in range(1,RD_RTCC.len()+1):
    for j in range(1,4):
         varname = 'STR_PCAR' + str(i)+"_"+str(j)
         ll = RD_RTCC[i][j]
         u.println("type is: %s" % str(type(ll)))
#         if isinstance(ll, str):
         if isinstance(ll, unicode):
             for c in ll:
                 u.println('**************** %d' % ord(c))
         value = str(RD_RTCC[i][j])
         globals()[varname]=value
##############################
## eos 1600                  #
##############################
RD_RTRC = sqlm(rtrc_sqlm%(I_peri_id,1))
if RD_RTRC.len():
    BD_PCAR_A =RD_RTRC[1][2]
    BD_PCAR_B =RD_RTRC[1][3]
##############################
## poano apo 1600                  #
##############################
RD_RTRC = sqlm(rtrc_sqlm%(I_peri_id,2))
if RD_RTRC.len():
    BD_PCAR_C =RD_RTRC[1][2]
    BD_PCAR_D =RD_RTRC[1][3]
##############################
## mobile           #
##############################
RD_RTRM = sqlm(rtrm_sqlm%(I_peri_id))
if RD_RTRM.len():
    BD_PMO_A =RD_RTRM[1][1]
    BD_PMO_B =RD_RTRM[1][2]
    BD_PMO_C =RD_RTRM[1][3]
    BD_PMO_D =RD_RTRM[1][4]
###################################
### stoixeia foron rtrd         #####
###################################
RD_RTRD_PARAM=rtrd_sqlm%(I_peri_id,I_peri_id)
RD_RTRD = sqlm(RD_RTRD_PARAM)
for i in range(1,RD_RTRD.len()+1):
    for j in range(1,4):
        varname22 = 'BD_PDU' + str(i)+str(j)
        if j<3:
            value22 = NVLZ(RD_RTRD[i][j])
        else:
            value22 = NVLZ(RD_RTRD[i][j-2])-NVLZ(RD_RTRD[i][j-1])
        globals()[varname22]=value22
