########## PARAMETROI     
I_peri_id = 1
D_date_from =date(2012,1,1)
var_today = date.today()
D_today  = var_today
### D_date_to =31/12/2012
I_year = D_date_from.year
I_month= D_date_from.month
I_day  = D_date_from.day
I_rtfe_session_id = 10
I_rtfh_id = 8
I_liquidation_rfch_id = 0
I_increment_percent = 0
I_comp_id = 1
I_rerp_id = 0
I_modifiable = 0
I_payment_value = 0
########################################
comp_sqls = """
SELECT *
FROM v_comp_all
WHERE COMP_ID=%d
"""
RD_COMP = sqls(comp_sqls%(I_comp_id))
STR_P001A = RD_COMP.TAOF_DESCRIPTION
STR_P001B =RD_COMP.TAOF_CODE
u.println("println works")
u.info("info works")
#elseif rd1_fixed.rtfd_code ='006' then
#STR_P006 = to_char(D_date_from,'yyyy');
