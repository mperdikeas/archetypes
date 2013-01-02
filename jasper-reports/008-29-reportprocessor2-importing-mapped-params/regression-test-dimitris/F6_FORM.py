###### паяалетяои INPUT######
I_PERI_ID = 1
I_PRRE_ID = 1
I_RETY_ID = 1
I_PROD_ID = 1
I_PRRE_REQUEST_NUMBER_001 = 100
STR_SUBM_SUBMISSION_DAY_002 = '20'
STR_SUBM_SUBMISSION_MONTH_002 = '12'
STR_SUBM_SUBMISSION_YEAR_002 = '12'
STR_RETY_ID_ETOS_003 = '11'
STR_RETY_ID_TROP_004 = ''
STR_RETY_ID_SYMP_004 = ''      
################################################################################
###### SQLS циа пимайа а KAI пимайа B ENTYPOY F6 ######
prod_sqls = """
select b.prod_surname                                                                          STR_PROD_SURNAME_101,
       b.prod_name                                                                             STR_PROD_NAME_102,
       substring(b.prod_father_name from 1 for 28)                                             STR_PROD_FATHER_NAME_103,
       substring(b.prod_street from 1 for 28)||' '||
       b.prod_number||' '||
       substring(b.prod_city from 1 for 28)                                                    STR_PROD_ADDRESS_106,
       substring(m.muni_description from 1 for 31)                                                         STR_MUNI_DESCRIPTION_107,
       b.prod_postal_code                                                                      STR_PROD_POSTAL_CODE_108,
       b.prod_phone                                                                            STR_PROD_PHONE_109,
       b.prod_tin                                                                              STR_PROD_TIN_110,
       t.idty_code                                                                             STR_IDTY_CODE_111,
       b.prod_identity                                                                         STR_PROD_IDENTITY_112,
       substring(o.taof_description from 1 for 29)                                             STR_TAOF_DESCRIPTION_113_1,
       o.taof_code                                                                             STR_TAOF_CODE_113_2,
       substring(b.prod_other_job from 1 for 52)                                               STR_PROD_OTHER_JOB_114,
       b.prod_address1                                                                         STR_PROD_ADDRESS1_115,
       coalesce(substring(m1.muni_description from 1 for 31),'')                               STR_MUNI_DESCRIPTION1_116,
       b.prod_postal_code1                                                                     STR_PROD_POSTAL_CODE1_117,
       b.prod_phone1                                                                           STR_PROD_PHONE1_118,
       case b.boct_id when 1 then 'X' else '' end                                              STR_BOCT_ID_1_119,
       case b.boct_id when 2 then 'X' else '' end                                              STR_BOCT_ID_2_119,
       case b.boct_id when 3 then 'X' else '' end                                              STR_BOCT_ID_3_119,
       coalesce(r.coop_name,'')                                                                STR_COOP_NAME_401,
       coalesce(r.coop_tin,'')                                                                 STR_COOP_TIN_402,
       coalesce(substring(r.coop_address from 1 for 41),'')                                    STR_COOP_ADDRESS_403,
       coalesce(substring(l.muni_description FROM 1 FOR 27),'')                                STR_MUNI_DESCRIPTION2_404,
       coalesce(r.coop_postal_code,'')                                                         STR_COOP_POSTAL_CODE_405,
       (case when b.coop_id is null then o.taof_code 
             else (select taof_code from er_tax_offices where taof_id = r.taof_id) end)        STR_TAOF_CODE_501_2,
       (case when b.coop_id is null then o.taof_description 
             else (select taof_description from er_tax_offices where taof_id = r.taof_id) end) STR_TAOF_DESCRIPTION_501_1,
       sum((case when v.fofc_id = 1 then coalesce(d.vrtd_value,0) else 0 end))                 BD_RETURN_VAT_201_1,
       max(case when v.fofc_id = 1 then f.fofc_vat_percent else 0 end)                         BD_FOFC_VAT_PERCENT_201_2,
       sum((case when v.fofc_id = 1 then round(cast(coalesce(d.vrtd_value,0) * coalesce(f.fofc_vat_percent,0) / 100 as numeric),2)
                 else 0 end))                                                                  BD_RETURN_VAT_VALUE_205_3,
       sum((case when v.fofc_id = 2 then coalesce(d.vrtd_value,0) 
                 else 0 end))                                                                  BD_RETURN_VAT_202_1,
       max(case when v.fofc_id = 2 then f.fofc_vat_percent else 0 end)                         BD_FOFC_VAT_PERCENT_202_2,
       sum((case when v.fofc_id = 2 then round(cast(coalesce(d.vrtd_value,0) * coalesce(f.fofc_vat_percent,0) / 100 as numeric),2)
                 else 0 end))                                                                  BD_RETURN_VAT_VALUE_206_3,
       sum((case when v.fofc_id = 3 then coalesce(d.vrtd_value,0) 
                 else 0 end))                                                                  BD_RETURN_VAT_203_1,
       max(case when v.fofc_id = 3 then f.fofc_vat_percent else 0 end)                         BD_FOFC_VAT_PERCENT_203_2,
       sum((case when v.fofc_id = 3 then round(cast(coalesce(d.vrtd_value,0) * coalesce(f.fofc_vat_percent,0) / 100 as numeric),2)
                 else 0 end))                                                                  BD_RETURN_VAT_VALUE_207_3,
       (select sum(vrth_invoice_count)                                                                        
          from er_vat_refund_trans_headers
         where prre_id = h.prre_id)                                                            BD_SUM_PLHTHOS
  from er_producers b left outer join er_cooperatives r on b.coop_id=r.coop_id
                      left outer join er_municipalities l on r.muni_id=l.muni_id
                      left outer join er_municipalities m on b.muni_id=m.muni_id
                      left outer join er_municipalities m1 on b.muni_id1=m1.muni_id,
       er_vat_refund_trans_headers h, 
       er_vat_refund_trans_details d,
       er_items i, 
       er_vat_categories v, 
       er_form_f6_columns f,
       er_identity_types t,
       er_tax_offices o
 Where b.prod_id = %d
   and h.prre_id = %d
   and d.vrth_id = h.vrth_id
   and i.item_id = d.item_id 
   and v.vaca_id = i.vaca_id 
   and f.fofc_id = v.fofc_id
   and t.idty_id = b.idty_id
   and o.taof_id = b.taof_id
 group by h.prre_id,
          b.prod_surname,
          b.prod_name,
          b.prod_father_name,
          b.prod_street,
          b.prod_number,
          b.prod_city,
          m.muni_description,
          b.prod_postal_code,
          b.prod_phone,
          b.prod_tin,
          t.idty_code,
          b.prod_identity,
          o.taof_description,
          o.taof_code,
          b.prod_other_job,
          b.prod_address1,
          m1.muni_description,
          b.prod_postal_code1,
          b.prod_phone1,
          r.coop_name,
          r.coop_tin,
          r.coop_address,
          l.muni_description,
          r.coop_postal_code,
          b.boct_id,
          b.coop_id,
          r.taof_id
"""
###### SQLS циа сумоко ж.п.а. аявийгс дгкысгс се пеяиптысг тяопопоигтийгс аитгсгс ######
first_prre_sqls = """
select sum(round(cast(coalesce(d.vrtd_value,0) * coalesce(f.fofc_vat_percent,0) / 100 as numeric),2))  BD_SUM_ARXIKH
  from er_producers_requests a,
       er_vat_refund_trans_headers h,
       er_vat_refund_trans_details d,
       er_items i,
       er_vat_categories v, 
       er_form_f6_columns f,
       er_submissions s
 where a.prod_id = %d
   and a.rety_id = 1
   and s.subm_id = a.subm_id
   and s.peri_id = %d
   and h.prre_id = a.prre_id
   and d.vrth_id = h.vrth_id
   and i.item_id = d.item_id 
   and v.vaca_id = i.vaca_id 
   and f.fofc_id = v.fofc_id
"""
###### rd_prod_sqls ######
rd_prod_sqls = sqls(prod_sqls%(I_PROD_ID,I_PRRE_ID))
###### rd_prod_sqls OUTPUT PARAMETERS ######
STR_PROD_SURNAME_101        = rd_prod_sqls.STR_PROD_SURNAME_101
STR_PROD_NAME_102           = rd_prod_sqls.STR_PROD_NAME_102
STR_PROD_FATHER_NAME_103    = rd_prod_sqls.STR_PROD_FATHER_NAME_103
STR_PROD_ADDRESS_106        = rd_prod_sqls.STR_PROD_ADDRESS_106
STR_MUNI_DESCRIPTION_107    = rd_prod_sqls.STR_MUNI_DESCRIPTION_107
STR_PROD_POSTAL_CODE_108    = rd_prod_sqls.STR_PROD_POSTAL_CODE_108
STR_PROD_PHONE_109          = rd_prod_sqls.STR_PROD_PHONE_109
STR_PROD_TIN_110            = rd_prod_sqls.STR_PROD_TIN_110
STR_IDTY_CODE_111           = rd_prod_sqls.STR_IDTY_CODE_111
STR_PROD_IDENTITY_112       = rd_prod_sqls.STR_PROD_IDENTITY_112
STR_TAOF_DESCRIPTION_113_1  = rd_prod_sqls.STR_TAOF_DESCRIPTION_113_1
STR_TAOF_CODE_113_2         = rd_prod_sqls.STR_TAOF_CODE_113_2
STR_PROD_OTHER_JOB_114      = rd_prod_sqls.STR_PROD_OTHER_JOB_114
STR_MUNI_DESCRIPTION1_116   = rd_prod_sqls.STR_MUNI_DESCRIPTION1_116
STR_PROD_POSTAL_CODE1_117   = rd_prod_sqls.STR_PROD_POSTAL_CODE1_117
STR_PROD_PHONE1_118         = rd_prod_sqls.STR_PROD_PHONE1_118
STR_BOCT_ID_1_119           = rd_prod_sqls.STR_BOCT_ID_1_119
STR_BOCT_ID_2_119           = rd_prod_sqls.STR_BOCT_ID_2_119
STR_BOCT_ID_3_119           = rd_prod_sqls.STR_BOCT_ID_3_119
STR_COOP_NAME_401           = rd_prod_sqls.STR_COOP_NAME_401
STR_COOP_TIN_402            = rd_prod_sqls.STR_COOP_TIN_402
STR_COOP_ADDRESS_403        = rd_prod_sqls.STR_COOP_ADDRESS_403
STR_MUNI_DESCRIPTION2_404   = rd_prod_sqls.STR_MUNI_DESCRIPTION2_404
STR_COOP_POSTAL_CODE_405    = rd_prod_sqls.STR_COOP_POSTAL_CODE_405
STR_TAOF_CODE_501_2         = rd_prod_sqls.STR_TAOF_CODE_501_2
STR_TAOF_DESCRIPTION_501_1  = rd_prod_sqls.STR_TAOF_DESCRIPTION_501_1
######
def BigDecimalToString(strVariable, value,flag):
    if globals()[strVariable] == 0:
        globals()['STR_' + strVariable] = ''
    else:
        if flag == 1:
            globals()['STR_' + strVariable] = ('%.2f' % value).rjust(70)
        else:
            globals()['STR_' + strVariable] = 'X' + ('%.2f%%' % value)
######		
RETURN_VAT_201_1 = rd_prod_sqls.BD_RETURN_VAT_201_1
BigDecimalToString('RETURN_VAT_201_1',RETURN_VAT_201_1,1)
FOFC_VAT_PERCENT_201_2 = rd_prod_sqls.BD_FOFC_VAT_PERCENT_201_2
BigDecimalToString('FOFC_VAT_PERCENT_201_2',FOFC_VAT_PERCENT_201_2,2)
STR_FOFC_VAT_PERCENT_201_2 = STR_FOFC_VAT_PERCENT_201_2.rjust(18)
RETURN_VAT_VALUE_205_3 = rd_prod_sqls.BD_RETURN_VAT_VALUE_205_3
BigDecimalToString('RETURN_VAT_VALUE_205_3', RETURN_VAT_VALUE_205_3,1)
######
RETURN_VAT_202_1 = rd_prod_sqls.BD_RETURN_VAT_202_1
BigDecimalToString('RETURN_VAT_202_1',RETURN_VAT_202_1,1)
FOFC_VAT_PERCENT_202_2 = rd_prod_sqls.BD_FOFC_VAT_PERCENT_202_2
BigDecimalToString('FOFC_VAT_PERCENT_202_2',FOFC_VAT_PERCENT_202_2,2)
RETURN_VAT_VALUE_206_3 = rd_prod_sqls.BD_RETURN_VAT_VALUE_206_3
BigDecimalToString('RETURN_VAT_VALUE_206_3',RETURN_VAT_VALUE_206_3,1)
######
RETURN_VAT_203_1 = rd_prod_sqls.BD_RETURN_VAT_203_1
BigDecimalToString('RETURN_VAT_203_1',RETURN_VAT_203_1,1)
FOFC_VAT_PERCENT_203_2 = rd_prod_sqls.BD_FOFC_VAT_PERCENT_203_2
BigDecimalToString('FOFC_VAT_PERCENT_203_2',FOFC_VAT_PERCENT_203_2,2)
RETURN_VAT_VALUE_207_3 = rd_prod_sqls.BD_RETURN_VAT_VALUE_207_3
BigDecimalToString('RETURN_VAT_VALUE_207_3',RETURN_VAT_VALUE_207_3,1)
######	
BD_SUM_201_202_203 = RETURN_VAT_201_1 + RETURN_VAT_202_1 + RETURN_VAT_203_1
BD_SUM_205_206_207 = RETURN_VAT_VALUE_205_3 + RETURN_VAT_VALUE_206_3 + RETURN_VAT_VALUE_207_3
BD_SUM_PLHTHOS = rd_prod_sqls.BD_SUM_PLHTHOS
if (I_RETY_ID == 2):
    ###### rd_first_prre_sqls ######
    rd_first_prre_sqls = sqls(first_prre_sqls%(I_PROD_ID,I_PERI_ID))
    ###### rd_first_prre_sqls OUTPUT PARAMETERS ######
    SUM_ARXIKH  = rd_first_prre_sqls.BD_SUM_ARXIKH
    STR_SUM_ARXIKH = ('%.2f' % SUM_ARXIKH).rjust(71)
    SUM_DIAFORA = SUM_ARXIKH - BD_SUM_205_206_207
    STR_SUM_DIAFORA = ('%.2f' % SUM_DIAFORA).rjust(74)
else:
    STR_SUM_ARXIKH  = ''
    STR_SUM_DIAFORA = ''