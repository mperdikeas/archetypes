{
 :connection
 {:classname "org.postgresql.Driver"
  :subprotocol "postgresql"
  :subname "//172.31.128.116:5444/cgaiaapps" ;;//172.31.129.29:5432/subsidies_II" ;; "//172.31.128.116:5444/usermgmnt" ;;"//172.31.128.116:5444/usermgmnt2" ;;"//localhost:5432/subsidies";;"//localhost:5432/cashflow"
  :user "gaia-user"
  :password "gaia-user-pwd"}
 :probe {
         :catalog "" ; interpreted as no catalog (that's different than null)
         :schema "vat_refund" ;; "measureactions"
         }
 :packagename "gr.neuropublic.usermgmnt.flatlib"
}