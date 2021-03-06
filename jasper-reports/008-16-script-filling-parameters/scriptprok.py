comp_id=1
###########
c1s = """ 
  SELECT P001A STR_P001A, ---(Κωδικός Αρμόδιας Δ.Ο.Υ.)
    p001b STR_P001B, ---(Αρμόδια Δ.Ο.Υ.)
    p006  STR_P006,  ---(Έτος)
    SUBSTRING(p007a FROM 1 FOR 2) STR_P007A_1, ---Ημερολογιακή Περίοδος από ημέρα
    SUBSTRING(p007a FROM 4 FOR 2) STR_P007A_2, ---Ημερολογιακή Περίοδος από μήνα
    SUBSTRING(p007a FROM 9 FOR 2) STR_P007A_3, ---Ημερολογιακή Περίοδος από έτος
    SUBSTRING(p007b FROM 1 FOR 2) STR_P007B_1, ---Ημερολογιακή Περίοδος έως ημέρα
    SUBSTRING(p007b FROM 4 FOR 2) STR_P007B_2, ---Ημερολογιακή Περίοδος έως μήνα
    SUBSTRING(p007b FROM 9 FOR 2) STR_P007B_3, ---Ημερολογιακή Περίοδος έως έτος
    CASE WHEN p008b = '1' THEN 'X' ELSE NULL END AS STR_P008B_1, ---(Φορολογική Περίοδος 1O Τρίμηνο)
    CASE WHEN p008b = '2' THEN 'X' ELSE NULL END AS STR_P008B_2, ---(Φορολογική Περίοδος 2O Τρίμηνο)
    CASE WHEN p008b = '3' THEN 'X' ELSE NULL END AS STR_P008B_3, ---(Φορολογική Περίοδος 3O Τρίμηνο)
    CASE WHEN p008b = '4' THEN 'X' ELSE NULL END AS STR_P008B_4, ---(Φορολογική Περίοδος 4O Τρίμηνο)
    CASE WHEN p010  = '1' THEN 'X' ELSE NULL END AS STR_P010,    ---(Είδος δήλωσης τροποποιητική)
    CASE WHEN p011  = '1' THEN 'X' ELSE NULL END AS STR_P011,    ---(Είδος δήλωσης με επιφύλαξη)
    CASE WHEN p012  = '1' THEN 'X' ELSE NULL END AS STR_P012_1,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '2' THEN 'X' ELSE NULL END AS STR_P012_2,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '3' THEN 'X' ELSE NULL END AS STR_P012_3,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '4' THEN 'X' ELSE NULL END AS STR_P012_4,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '5' THEN 'X' ELSE NULL END AS STR_P012_5,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '6' THEN 'X' ELSE NULL END AS STR_P012_6,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '7' THEN 'X' ELSE NULL END AS STR_P012_7,  ---(Έκτακτη Δήλωση)
    CASE WHEN p012  = '8' THEN 'X' ELSE NULL END AS STR_P012_8,  ---(Έκτακτη Δήλωση)
    p009 STR_P009, ---(Μήνας Ενδοκοινοτικών Συναλλαγών)
    p013 STR_P013, ---(Είδος Απαλλαγής/Έτος/Αρ. πρωτ. αρχικής αίτησης)
    p101 STR_P101, ---(Επώνυμο ή Επωνυμία)
    p102 STR_P102, ---(Όνομα)
    p103 STR_P103, ---(Όνομα Πατέρα)
    p104 STR_P104, ---(Α.Φ.Μ.)
    p301 BD_P301, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 13%))
    p302 BD_P302, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 6.5%))
    p303 BD_P303, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 23%))
    p304 BD_P304, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 9%)) 
    p305 BD_P305, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 5%))
    p306 BD_P306, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 16%))
    p307 BD_P307, ---(Σύνολο φορολογητέων εκροών)
    p308 BD_P308, ---(Εκροές φορολογητέες εκτός Ελλάδας) 
    p309 BD_P309, ---(Ενδοκοινοτικές παραδόσεις, εξαγωγές και λοιπές εκροές)
    p310 BD_P3010, ---(Εκροές απαλλασσόμενες και εξαιρούμενες χωρίς δικαίωμα έκπτωσης)
    p311 BD_P3011, ---(Σύνολο Εκροών)
    p331 BD_P331, ---(Ο φόρος που αναλογεί στον κωδικό 301)
    p332 BD_P332, ---(Ο φόρος που αναλογεί στον κωδικό 302)
    p333 BD_P333, ---(Ο φόρος που αναλογεί στον κωδικό 303)
    p334 BD_P334, ---(Ο φόρος που αναλογεί στον κωδικό 304)
    p335 BD_P335, ---(Ο φόρος που αναλογεί στον κωδικό 305)
    p336 BD_P336, ---(Ο φόρος που αναλογεί στον κωδικό 306)
    p337 BD_P337, ---(Σύνολο Φόρου)
    p351 BD_P351, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 13%)
    p352 BD_P352, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 6.5%)
    p353 BD_P353, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 23%)
    p354 BD_P354, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 9%)
    p355 BD_P355, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 5%)
    p356 BD_P356, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 16%)
    p357 BD_P357, ---(Φορολογητέες δαπάνες & γενικά έξοδα)
    p358 BD_P358, ---(Σύνολο φορολογητέων εισροών)
    p371 BD_P371, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 351)
    p372 BD_P372, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 352)
    p373 BD_P373, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 353)
    p374 BD_P374, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 354)
    p375 BD_P375, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 355)
    p376 BD_P376, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 356)
    p377 BD_P377, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 357)
    p378 BD_P378, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 358)
    p341 BD_P341, ---(Συνολικές Ενδοκοινοτικές Αποκτήσεις)
    p342 BD_P342, ---(Συνολικές Ενδοκοινοτικές Παραδόσεις)
    p343 BD_P343, ---(Πράξεις λήπτη αγαθών και υπηρεσιών)
    p344 BD_P344, ---(Ενδοκοινοτικές λήψεις υπηρεσιών αρθρ.14 παρ.2α) 
    p345 BD_P345, ---(Ενδοκοινοτικές παροχές υπηρεσιών αρθρ.14 παρ.2)
    p346 BD_P346, ---(Πωλήσεις αγροτών του άρθρου 41 με το κανονικό καθεστώς)
    p400 BD_P400, ---(Κωδικός [346] Χ 5% (συντελεστή επιστροφής))
    p401 BD_P401, ---(Πιστωτικό υπόλοιπο προηγούμενης φορολογικής περιόδου)
    p402 BD_P402, ---(Φόρος έκτακτης δήλωσης / Λοιπά προστιθέμενα ποσά)
    p403 BD_P403, ---(Χρεωστικό Αρχικής Δήλωσης)
    p404 BD_P404, ---(Σύνολο 401-403)
    p411 BD_P411, ---(Φόρος εισροών που πρέπει να μειωθεί)
    p412 BD_P412, ---(Χρεωστικό μέχρι 3€ προηγούμενης φορολογικής περιόδου & λοιπά αφαιρούμενα ποσά)
    p413 BD_P413, ---(Σύνολο 411-412)
    p420 BD_P420, ---(Υπόλοιπο φόρου εισροών)
    p501 BD_P501, ---(Πιστωτικό Υπόλοιπο)
    p502 BD_P502, ---(Ποσό για έκπτωση)
    p503 BD_P503, ---(Ποσό για επιστροφή)
    p511 BD_P511, ---(Χρεωστικό υπόλοιπο)
    p512 BD_P512, ---(Προσαύξηση εκπρόθεσμης υποβολής)
    p514 BD_P514, ---(Προσαύξηση υπολοίπου 2%)
    p513 BD_P513, ---(Σύνολο για καταβολή)
    p521 BD_P521, ---(Πρώτη Δόση)
    p522 BD_P522 ---(Υπόλοιπο Ποσό)
  FROM v_p_fpa_2012
  where rtfe_session_id=10
"""
c1sfilled = c1s
curr1 = sqls(c1sfilled)
STR_P001A = curr1.STR_P001A
STR_P001B = curr1.STR_P001B
STR_P006 = curr1.STR_P006
STR_P007A_1 = STR_P007A_1
STR_P007A_2 = STR_P007A_2
STR_P007A_3 = STR_P007A_3
STR_P007B_1 = STR_P007B_1
STR_P007B_2 = STR_P007B_2
STR_P007B_3 = STR_P007B_3
STR_P008B_1 = STR_P008B_1
STR_P008B_2 = STR_P008B_2
STR_P008B_3 = STR_P008B_3
STR_P008B_4 = STR_P008B_4
STR_P010 = STR_P010
STR_P011 = STR_P011
STR_P012_1 = STR_P012_1
STR_P012_2 = STR_P012_2
STR_P012_3 = STR_P012_3
STR_P012_4 = STR_P012_4
STR_P012_5 = STR_P012_5
STR_P012_6 = STR_P012_6
STR_P012_7 = STR_P012_7
STR_P012_8 = STR_P012_8
STR_P009 = STR_P009
STR_P013 = STR_P013
STR_P101 = STR_P101
STR_P102 = STR_P102
STR_P103 = STR_P103
STR_P104 = STR_P104
BD_P301 = BD_P301
BD_P302 = BD_P302
BD_P303 = BD_P303
BD_P304 = BD_P304
BD_P305 = BD_P305
BD_P306 = BD_P306
BD_P307 = BD_P307
BD_P308 = BD_P308
BD_P309 = BD_P309
BD_P3010 = BD_P3010
BD_P3011 = BD_P3011
BD_P331 = BD_P331
BD_P332 = BD_P332
BD_P333 = BD_P333
BD_P334 = BD_P334
BD_P335 = BD_P335
BD_P336 = BD_P336
BD_P337 = BD_P337
BD_P351 = BD_P351
BD_P352 = BD_P352
BD_P353 = BD_P353
BD_P354 = BD_P354
BD_P355 = BD_P355
BD_P356 = BD_P356
BD_P357 = BD_P357
BD_P358 = BD_P358
BD_P371 = BD_P371
BD_P372 = BD_P372
BD_P373 = BD_P373
BD_P374 = BD_P374
