import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;

import mutil.base.ExceptionAdapter;
import mutil.jdbc.JdbcUtils;

import org.apache.commons.lang3.StringEscapeUtils;

public class QueryJythonHolder {

    /*    private String driver;
    private String connectString;
    private String user;
    private String password;

    private QueryJythonHolder(String driver, String connectString, String user, String password) throws ClassNotFoundException {
        this.driver = driver;
        this.connectString = connectString;
        this.user = user;
        this.password = password;
        Class.forName(driver);
        }*/

    private Connection conn;
    public QueryJythonHolder(Connection conn) {
        this.conn = conn;
    }

    /*    private static QueryJythonHolder singleton;

    public static void initializeHolder(String driver, String connectString, String user, String password) {
        if (singleton != null) throw new RuntimeException();
        singleton = new QueryJythonHolder(driver, connectString, user, password);
    }

    public static Map<Integer, Object> q(String query) throws SQLException {
        return singleton.query(query);
    }*/

    public String escape(String foo) {
        return StringEscapeUtils.escapeJava(foo);
    }

    public Map<Integer, Object> q(String query) throws SQLException {
        query = StringEscapeUtils.unescapeJava(query);
        System.out.println("running query:\n"+query);
        //Connection conn = DriverManager.getConnection(connectString, user, password);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, Object> retValue = new HashMap<Integer, Object>();
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            boolean haveBeenHereBefore = false;
            while (rs.next()) {
                if (haveBeenHereBefore) throw new RuntimeException("more than one row returned from query: "+query);
                for (int i = 1 ; i <= columnsNumber ; i++)
                    retValue.put(i, rs.getObject(i));                                    
                haveBeenHereBefore = true;
            }
            return retValue;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            //JdbcUtils.closeConnection(conn);
        }
    }
}