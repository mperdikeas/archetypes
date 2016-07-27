package a.b.c.typeA.dbdal;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.io.IOException;
import java.sql.Connection; import java.sql.SQLException; import java.sql.PreparedStatement; import java.sql.ResultSet; import java.sql.Statement;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
    
import org.apache.commons.dbutils.DbUtils;
import com.google.common.io.CharStreams;

import mutil.base.ShowStopper;

import mutil.jdbc.BaseDBFacade;
import mutil.jdbc.JdbcUtils;


public class DBDAL extends BaseDBFacade implements IDBDAL {

    public DBDAL(DataSource _ds) {
        if (_ds == null)
            throw new ShowStopper();
        this.ds = _ds;
    }

    @Override
    protected boolean enableLogging() {
        return false;
    }



    @Override
    public Connection exportConnection() {
        try {
            return getConnection();
        } catch (SQLException e) {
            throw new ShowStopper(e);
        }
    }

    @Override
    public void closeExportedConnection(Connection conn) {
        DbUtils.closeQuietly(conn);
    }


    @Override
    public List<Person> listPersons() {
        Connection        conn = null;
        PreparedStatement ps   = null;
        ResultSet         rs   = null;
        try {
            conn = getConnection();
            final String SQL =  "SELECT a.i, a.fname, a.lname, a.comments, a.yearOfBirth \n"+
                                "FROM typea.person a                                     \n"+
                                "ORDER BY a.i                                              ";
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();
            List<Person> rv = new ArrayList<>();
            while (rs.next()) {
                int i           = JdbcUtils.rs_getInt(rs, "i");
                String fname    =           rs.getString("fname");
                String lname    =           rs.getString("lname");
                String comments =           rs.getString("comments");
                int yearOfBirth = JdbcUtils.rs_getInt(rs, "yearOfBirth");
                rv.add(new Person(i, fname, lname, comments, yearOfBirth));
            }
            return rv;
        } catch (SQLException e) {
            throw new ShowStopper(e);
        } finally {
            DbUtils.closeQuietly(conn, ps, rs);
        }
    }

    @Override
    public void modifyPerson(Person person) {
        Connection        conn = null;
        PreparedStatement ps   = null;
        try {
            conn = getConnection();
            final String SQL =  "UPDATE typea.person                                     \n"+
                                "SET fname = ?, lname = ?, comments = ?, yearOfBirth = ? \n"+
                                "WHERE i=?                                                 ";
            ps = conn.prepareStatement(SQL);
            ps.setString       (    1, person.fname);
            ps.setString       (    2, person.lname);
            ps.setString       (    3, person.comments);
            ps.setInt          (    4, person.yearOfBirth);
            ps.setInt          (    5, person.i);
            if (ps.executeUpdate() != 1) {
                rollback(conn);
                throw new ShowStopper();
            }
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            throw new ShowStopper(e);
        } finally {
            DbUtils.closeQuietly(conn, ps, (ResultSet) null);
        }
    }

    @Override
    public int createNewPerson(PersonBase person) {
        Connection        conn              = null;
        PreparedStatement ps                = null;
        ResultSet         rs                = null;
        Integer           autoIncKeyFromApi = null;        
        try {
            conn = getConnection();
            final String SQL =  "INSERT INTO typea.person(fname, lname, comments, yearofbirth) \n"+
                                "VALUES                  (    ?,     ?,        ?,           ?)   ";
            ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString       (    1, person.fname);
            ps.setString       (    2, person.lname);
            ps.setString       (    3, person.comments);
            ps.setInt          (    4, person.yearOfBirth);
            if (ps.executeUpdate() != 1) {
                rollback(conn);
                throw new ShowStopper();
            }
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                autoIncKeyFromApi = rs.getInt(1);
            } else {
                throw new ShowStopper();
            }
            conn.commit();
            return autoIncKeyFromApi;
        } catch (SQLException e) {
            rollback(conn);
            throw new ShowStopper(e);
        } finally {
            DbUtils.closeQuietly(conn, ps, (ResultSet) null);
        }
    }

    @Override
    public boolean deletePerson (int i) {
        Connection        conn              = null;        
        PreparedStatement ps                = null;
        try {
            conn = getConnection();
            final String SQL =  "DELETE FROM typea.person \n"+
                                "WHERE i = ?                ";
            ps = conn.prepareStatement(SQL);
            ps.setInt          (    1, i);
            int rowCount = ps.executeUpdate();
            if (!Arrays.asList(0,1).contains(rowCount))
                throw new ShowStopper(String.format("Impossible affected row count: [%d]", rowCount));
            conn.commit();
            if (rowCount==1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            rollback(conn);
            throw new ShowStopper(e);
        } finally {
            DbUtils.closeQuietly(conn, ps, (ResultSet) null);
        }        
    }
}
