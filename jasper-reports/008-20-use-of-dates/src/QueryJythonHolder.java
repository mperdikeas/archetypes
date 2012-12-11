import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import mutil.base.ExceptionAdapter;
import mutil.jdbc.JdbcUtils;

import org.apache.commons.lang3.StringEscapeUtils;

public class QueryJythonHolder {

    private Connection conn;
    public QueryJythonHolder(Connection conn) {
        this.conn = conn;
    }

    public String escape(String foo) {
        return StringEscapeUtils.escapeJava(foo);
    }

    public Map<Integer, Object> sqls(String query) throws SQLException {
        Map<Integer, Map<Integer, Object>> retValue = sqlm(query, 1, true, true);
        return retValue.get(1);
    }

    private StringBuffer queries = new StringBuffer();

    public String getQueries() { return queries.toString(); }

    public Object bdToFloat(Object o) {
        if (o == null) return null;
        else if (o.getClass().equals(BigDecimal.class)) return ((BigDecimal) o).floatValue();
        else return o;
    }

    public Map<Integer, Map<Integer, Object>> sqlm(String query, int numOfRows, boolean panicIfLess, boolean panicIfMore) throws SQLException {
        query = StringEscapeUtils.unescapeJava(query);
        System.out.println(query);
        queries.append(query+"\n");
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, Map<Integer, Object>> retValue = new HashMap<Integer, Map<Integer, Object>>();
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            int rowNum = 0 ;    
            while (rs.next()) {
                ExtendedHashMap insideRetValue = new ExtendedHashMap();
                if (++rowNum > numOfRows) {
                    if (panicIfMore)
                        throw new RuntimeException(String.format("more than the requested number of rows (%d) returned from query:\n    %s\n...and panicIfMore is true", numOfRows, query));
                    else
                        break;
                }
                for (int i = 1 ; i <= columnsNumber ; i++) {
                    Object o = rs.getObject(i);
                    insideRetValue.put(i, rsmd.getColumnName(i), bdToFloat(rs.getObject(i)));
                }
                retValue.put(rowNum, insideRetValue);
            }
            if ((rowNum < numOfRows) && panicIfLess)
                throw new RuntimeException(String.format("less than the requested number of rows (%d) returned from query:\n    %s\n...and panicIfLess is true", numOfRows, query));
            return retValue;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            //JdbcUtils.closeConnection(conn);
        }
    }


    public static final String PROBLEM_MONEY_QUERY_1 = "SELECT * from v_p_fpa_2012";
    public static final String PROBLEM_MONEY_QUERY_2  = " SELECT P001A STR_P001A, ---(Κωδικός Αρμόδιας Δ.Ο.Υ.)\n"+
"    p001b STR_P001B, ---(Αρμόδια Δ.Ο.Υ.)\n"+
"    p006  STR_P006,  ---(Έτος)\n"+
"    SUBSTRING(p007a FROM 1 FOR 2) STR_P007A_1, ---Ημερολογιακή Περίοδος από ημέρα\n"+
"    SUBSTRING(p007a FROM 4 FOR 2) STR_P007A_2, ---Ημερολογιακή Περίοδος από μήνα\n"+
"    SUBSTRING(p007a FROM 9 FOR 2) STR_P007A_3, ---Ημερολογιακή Περίοδος από έτος\n"+
"    SUBSTRING(p007b FROM 1 FOR 2) STR_P007B_1, ---Ημερολογιακή Περίοδος έως ημέρα\n"+
"    SUBSTRING(p007b FROM 4 FOR 2) STR_P007B_2, ---Ημερολογιακή Περίοδος έως μήνα\n"+
"    SUBSTRING(p007b FROM 9 FOR 2) STR_P007B_3, ---Ημερολογιακή Περίοδος έως έτος\n"+
"    CASE WHEN p008b = '1' THEN 'X' ELSE NULL END AS STR_P008B_1, ---(Φορολογική Περίοδος 1O Τρίμηνο)\n"+
"    CASE WHEN p008b = '2' THEN 'X' ELSE NULL END AS STR_P008B_2, ---(Φορολογική Περίοδος 2O Τρίμηνο)\n"+
"    CASE WHEN p008b = '3' THEN 'X' ELSE NULL END AS STR_P008B_3, ---(Φορολογική Περίοδος 3O Τρίμηνο)\n"+
"    CASE WHEN p008b = '4' THEN 'X' ELSE NULL END AS STR_P008B_4, ---(Φορολογική Περίοδος 4O Τρίμηνο)\n"+
"    CASE WHEN p010  = '1' THEN 'X' ELSE NULL END AS STR_P010,    ---(Είδος δήλωσης τροποποιητική)\n"+
"    CASE WHEN p011  = '1' THEN 'X' ELSE NULL END AS STR_P011,    ---(Είδος δήλωσης με επιφύλαξη)\n"+
"    CASE WHEN p012  = '1' THEN 'X' ELSE NULL END AS STR_P012_1,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '2' THEN 'X' ELSE NULL END AS STR_P012_2,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '3' THEN 'X' ELSE NULL END AS STR_P012_3,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '4' THEN 'X' ELSE NULL END AS STR_P012_4,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '5' THEN 'X' ELSE NULL END AS STR_P012_5,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '6' THEN 'X' ELSE NULL END AS STR_P012_6,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '7' THEN 'X' ELSE NULL END AS STR_P012_7,  ---(Έκτακτη Δήλωση)\n"+
"    CASE WHEN p012  = '8' THEN 'X' ELSE NULL END AS STR_P012_8,  ---(Έκτακτη Δήλωση)\n"+
"    p009 STR_P009, ---(Μήνας Ενδοκοινοτικών Συναλλαγών)\n"+
"    p013 STR_P013, ---(Είδος Απαλλαγής/Έτος/Αρ. πρωτ. αρχικής αίτησης)\n"+
"    p101 STR_P101, ---(Επώνυμο ή Επωνυμία)\n"+
"    p102 STR_P102, ---(Όνομα)\n"+
"    p103 STR_P103, ---(Όνομα Πατέρα)\n"+
"    p104 STR_P104, ---(Α.Φ.Μ.)\n"+
"    p301 BD_P301, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 13%))\n"+
"    p302 BD_P302, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 6.5%))\n"+
"    p303 BD_P303, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 23%))\n"+
"    p304 BD_P304, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 9%)) \n"+
"    p305 BD_P305, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 5%))\n"+
"    p306 BD_P306, ---(Αξία των φορολογητέων εκροών (κατά συντελεστή ΦΠΑ 16%))\n"+
"    p307 BD_P307, ---(Σύνολο φορολογητέων εκροών)\n"+
"    p308 BD_P308, ---(Εκροές φορολογητέες εκτός Ελλάδας) \n"+
"    p309 BD_P309, ---(Ενδοκοινοτικές παραδόσεις, εξαγωγές και λοιπές εκροές)\n"+
"    p310 BD_P3010, ---(Εκροές απαλλασσόμενες και εξαιρούμενες χωρίς δικαίωμα έκπτωσης)\n"+
"    p311 BD_P3011, ---(Σύνολο Εκροών)\n"+
"    p331 BD_P331, ---(Ο φόρος που αναλογεί στον κωδικό 301)\n"+
"    p332 BD_P332, ---(Ο φόρος που αναλογεί στον κωδικό 302)\n"+
"    p333 BD_P333, ---(Ο φόρος που αναλογεί στον κωδικό 303)\n"+
"    p334 BD_P334, ---(Ο φόρος που αναλογεί στον κωδικό 304)\n"+
"    p335 BD_P335, ---(Ο φόρος που αναλογεί στον κωδικό 305)\n"+
"    p336 BD_P336, ---(Ο φόρος που αναλογεί στον κωδικό 306)\n"+
"    p337 BD_P337, ---(Σύνολο Φόρου)\n"+
"    p351 BD_P351, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 13%)\n"+
"    p352 BD_P352, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 6.5%)\n"+
"    p353 BD_P353, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 23%)\n"+
"    p354 BD_P354, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 9%)\n"+
"    p355 BD_P355, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 5%)\n"+
"    p356 BD_P356, ---(Αξία των φορολογητέων εισροών κατά συντελεστή ΦΠΑ 16%)\n"+
"    p357 BD_P357, ---(Φορολογητέες δαπάνες & γενικά έξοδα)\n"+
"    p358 BD_P358, ---(Σύνολο φορολογητέων εισροών)\n"+
"    p371 BD_P371, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 351)\n"+
"    p372 BD_P372, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 352)\n"+
"    p373 BD_P373, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 353)\n"+
"    p374 BD_P374, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 354)\n"+
"    p375 BD_P375, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 355)\n"+
"    p376 BD_P376, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 356)\n"+
"    p377 BD_P377, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 357)\n"+
"    p378 BD_P378, ---(Ο φόρος εισροών που αναλογεί στον Κωδικό 358)\n"+
"    p341 BD_P341, ---(Συνολικές Ενδοκοινοτικές Αποκτήσεις)\n"+
"    p342 BD_P342, ---(Συνολικές Ενδοκοινοτικές Παραδόσεις)\n"+
"    p343 BD_P343, ---(Πράξεις λήπτη αγαθών και υπηρεσιών)\n"+
"    p344 BD_P344, ---(Ενδοκοινοτικές λήψεις υπηρεσιών αρθρ.14 παρ.2α) \n"+
"    p345 BD_P345, ---(Ενδοκοινοτικές παροχές υπηρεσιών αρθρ.14 παρ.2)\n"+
"    p346 BD_P346, ---(Πωλήσεις αγροτών του άρθρου 41 με το κανονικό καθεστώς)\n"+
"    p400 BD_P400, ---(Κωδικός [346] Χ 5% (συντελεστή επιστροφής))\n"+
"    p401 BD_P401, ---(Πιστωτικό υπόλοιπο προηγούμενης φορολογικής περιόδου)\n"+
"    p402 BD_P402, ---(Φόρος έκτακτης δήλωσης / Λοιπά προστιθέμενα ποσά)\n"+
"    p403 BD_P403, ---(Χρεωστικό Αρχικής Δήλωσης)\n"+
"    p404 BD_P404, ---(Σύνολο 401-403)\n"+
"    p411 BD_P411, ---(Φόρος εισροών που πρέπει να μειωθεί)\n"+
"    p412 BD_P412, ---(Χρεωστικό μέχρι 3€ προηγούμενης φορολογικής περιόδου & λοιπά αφαιρούμενα ποσά)\n"+
"    p413 BD_P413, ---(Σύνολο 411-412)\n"+
"    p420 BD_P420, ---(Υπόλοιπο φόρου εισροών)\n"+
"    p501 BD_P501, ---(Πιστωτικό Υπόλοιπο)\n"+
"    p502 BD_P502, ---(Ποσό για έκπτωση)\n"+
"    p503 BD_P503, ---(Ποσό για επιστροφή)\n"+
"    p511 BD_P511, ---(Χρεωστικό υπόλοιπο)\n"+
"    p512 BD_P512, ---(Προσαύξηση εκπρόθεσμης υποβολής)\n"+
"    p514 BD_P514, ---(Προσαύξηση υπολοίπου 2%)\n"+
"    p513 BD_P513, ---(Σύνολο για καταβολή)\n"+
"    p521 BD_P521, ---(Πρώτη Δόση)\n"+
"    p522 BD_P522 ---(Υπόλοιπο Ποσό)\n"+
"  FROM v_p_fpa_2012\n"+
"  where rtfe_session_id=10";
    public static final String PROBLEM_QUERIES[] = {PROBLEM_MONEY_QUERY_1, PROBLEM_MONEY_QUERY_2};

    public void testProblemQueries() throws SQLException {
        int i = 0 ;
        for (String problemQuery : PROBLEM_QUERIES) {
            System.out.println("trying problem query "+(++i));
            sqlm(problemQuery, 5, false, false);
        }
    }

}