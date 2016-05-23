package d.e.f;

import java.sql.Connection; import java.sql.SQLException; import java.sql.PreparedStatement; import java.sql.ResultSet; import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

public class DBDAL extends BaseDBFacade implements IDBDAL {

    public DBDAL(DataSource _ds) {
        if (_ds == null)
            throw new RuntimeException();
        this.ds = _ds;
    }

    @Override
    public String retrieve(String key) {
        Connection        conn = null;
        PreparedStatement ps   = null;
        ResultSet         rs   = null;
        try {
            conn = getConnection();
            String SQL = "SELECT value FROM silly_schema.keyvalue WHERE key=?";
            ps = conn.prepareStatement(SQL);
            ps.setString(1, key);
            rs = ps.executeQuery();
            String rv = null;
            boolean beenHere = false;
            while (rs.next())
                if (beenHere)
                    throw new RuntimeException(String.format("[%s]", key));
                else {
                    beenHere = true;
                    rv = rs.getString("value");
                }
            // artificial pause for testing
            System.out.printf("[%s] going to sleep\n", System.identityHashCode(this));
            try {Thread.sleep(30*1000);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.printf("[%s] waking\n"        , System.identityHashCode(this));            
            return rv;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);
        }
    }



}
