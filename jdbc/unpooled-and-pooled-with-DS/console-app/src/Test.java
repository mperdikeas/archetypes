import java.sql.*;
import javax.sql.DataSource;
import java.util.Properties;
import org.apache.commons.dbutils.DbUtils;

import org.apache.commons.dbcp.BasicDataSource;

public class Test {

    private static final String PROTOCOL = "jdbc";
    private static final String DBTYPE   = "postgresql";
    private static final String URL      = "localhost";
    private static final int    PORT     = 5432;
    private static final String DB       = "timetests";

    private static final String url() {
        return String.format("%s:%s://%s:%d/%s", PROTOCOL, DBTYPE ,URL, PORT, DB);
    }
    private static final String USERNAME = "gaia-user";
    private static final String PASSWORD = "gaia-user-pwd";

    public static void main(String args[]) throws SQLException {
        final int N = Integer.valueOf(args[0]);
        {
            System.out.println("POOL-LESS QUERIES");
            int connSoFar = 0 ;
            try {
                final long L1 = System.currentTimeMillis();
                for (int i = 0 ; i < N ; i++) {
                    nopoolQuery();
                    connSoFar++;
                }
                final long L2 = System.currentTimeMillis();
                System.out.println(String.format("%d poolless queries in %d millis", N, L2-L1));
            } finally {
                System.out.println(String.format("%d connections opened", connSoFar));
            }
        }
        {
            System.out.println("POOLED QUERIES");
            int connSoFar = 0 ;
            try {
                final long L1 = System.currentTimeMillis();
                BasicDataSource dataSource = new BasicDataSource();

                dataSource.setDriverClassName("org.postgresql.Driver");
                dataSource.setUsername(USERNAME);
                dataSource.setPassword(PASSWORD);
                dataSource.setUrl(url());
                dataSource.setMaxActive(1);   // only one should be enough in this application
                dataSource.setMaxIdle(5);
                dataSource.setInitialSize(1); // only one should be enough in this application
                dataSource.setValidationQuery("SELECT 1");

                for (int i = 0 ; i < N ; i++) {
                    pooledQuery(dataSource);
                    connSoFar++;
                }
                final long L2 = System.currentTimeMillis();
                System.out.println(String.format("%d pooled queries in %d millis", N, L2-L1));
            } finally {
                System.out.println(String.format("%d connections opened", connSoFar));
            }
        }
    }

    private static void nopoolQuery() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String connUrl = url();
            Properties connectionProps = new Properties();
            connectionProps.put("user"    , USERNAME);
            connectionProps.put("password", PASSWORD);
    
            conn = DriverManager.getConnection(connUrl, connectionProps);
    
    
            ps = conn.prepareStatement("select * from variousdatetimes");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id  = rs.getInt   ( 1);
            }
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);
        }
    }

    private static void pooledQuery(DataSource ds) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from variousdatetimes");
            rs = ps.executeQuery();
            while (rs.next()) {
                int id  = rs.getInt   ( 1);
            }
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);
        }
    }
}