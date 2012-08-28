package name.brucephillips.nosecurity.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import name.brucephillips.nosecurity.model.*;

public class UserDAO
{
  
    
    public static List<User> getUser(String userName)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
    	
    	  

  	      
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List <User>usersList = new ArrayList<User>();
        
        String query = "SELECT * FROM users " +
                       "WHERE username = ?";
        try
        {
        	
        	
            ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setUserID(Long.valueOf(rs.getString("userid") ) );
                user.setPassword(rs.getString("password") );
                
                usersList.add(user);
            }
            return usersList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static List <User> getAllUsers()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
    	
    	//Connection connection;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List <User>usersList = new ArrayList<User>();
        
        String query = "SELECT * FROM users ";
        try
        {
        	//connection = ConnectionFactory.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setUserID(Long.valueOf(rs.getString("userid") ) );
                user.setPassword(rs.getString("password") );
                usersList.add(user);
            }
            return usersList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            //pool.freeConnection(connection);
        }
    }
}