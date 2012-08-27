package name.brucephillips.nosecurity.dao;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;

public class ConnectionPool
{
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
 
    private ConnectionPool()
    {
        try
        {
            InitialContext ic = new InitialContext();
            //dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/security");  // this one fails with NPE (null pointer exception) on the connection
            dataSource = (DataSource) ic.lookup("java:/jsf005db");     // this one gets the connection but, at the moment, doesn't find the table (natural, since it doesn't exist)
            //dataSource = (DataSource) ic.lookup("java:comp/env/jsf005db"); // this one fails too with NPE

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance()
    {
        if (pool == null)
        {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection()
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }
    
    public void freeConnection(Connection c)
    {
        try
        {
            c.close();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}