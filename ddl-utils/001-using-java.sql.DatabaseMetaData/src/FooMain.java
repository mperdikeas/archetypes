import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import org.apache.commons.dbcp.BasicDataSource;


public class FooMain {


private static final String PROTOCOL = "jdbc";
private static final String DBTYPE   = "postgresql";
private static final String url(String machine, int port, String db) {
    return String.format("%s:%s://%s:%d/%s", PROTOCOL, DBTYPE ,machine, port, db);
}
private static final String USERNAME = "gaia-user";
private static final String PASSWORD = "gaia-user-pwd";
private static DataSource getDataSource(String machine, int port, String db) throws SQLException {
    BasicDataSource dS = new BasicDataSource();
    dS.setDriverClassName("org.postgresql.Driver");
    dS.setUsername(USERNAME);
    dS.setPassword(PASSWORD);
    dS.setUrl(url(machine, port, db));
    dS.setMaxActive(2);   // 2 should be enough for this app
    dS.setMaxIdle(5);
    dS.setInitialSize(1); // 1 should be enough for this app
    dS.setValidationQuery("SELECT 1");
    return dS;
}


public static void main(String args[]) throws SQLException {
    DataSource ds       = null;
    if (args.length != 3 )
        throw new RuntimeException("expects 3 arguments: host port db");
    else {
        ds        = getDataSource(args[0], Integer.valueOf(args[1]), args[2]);
    }
    Connection conn = null;
    try {
        conn = ds.getConnection();
        DatabaseMetaData mtd = conn.getMetaData();
        printSchemas(mtd);
        System.out.println("\n\n    **** TABLES FOLLOW:    ****\n\n");
        printTables(mtd, "measureactions");
    } finally {
        DbUtils.closeQuietly(conn);
    }
}

public static void printSchemas(DatabaseMetaData mtd) throws SQLException {
    ResultSet schemas = mtd.getSchemas();
    while (schemas.next()) {
        System.out.println(String.format("%s %s", schemas.getString("TABLE_SCHEM"), schemas.getString("TABLE_CATALOG")));
    }
}

public static void printTables(DatabaseMetaData mtd, String schema) throws SQLException {
    ResultSet tables = mtd.getTables(null, schema, null, null); // new String[]{"TABLE", "VIEW"});
    while (tables.next())
        System.out.println( tables.getString("TABLE_NAME") );
}
}