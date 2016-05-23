package d.e.f;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class BaseDBFacade {
    
    protected DataSource ds;

    protected Connection getConnection(int transactionIsolationLevel) throws SQLException {
        final boolean DEBUG = true;
        if (DEBUG) System.out.println("asking for connection ... ");
        long l1 = System.currentTimeMillis();
        Connection conn = ds.getConnection();
        long l2 = System.currentTimeMillis();
        if (DEBUG) System.out.printf(" ... connection obtained after %d millis\n", l2-l1);
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(transactionIsolationLevel);
        return conn;
    }



    protected Connection getConnection() throws SQLException {
        return getConnection(Connection.TRANSACTION_READ_COMMITTED);
                          // Connection.TRANSACTION_REPEATABLE_READ
    }

    protected static void rollback(Connection conn) {
        try {
            DbUtils.rollback(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void commit(Connection conn) {
        try {
            if (conn!=null)
                conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
