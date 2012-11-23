package name.brucephillips.nosecurity.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import name.brucephillips.nosecurity.dao.UserDAO;
import name.brucephillips.nosecurity.model.User;



/**
 * If user associated with this request is 
 * authenticated then gets all users from data store
 * and forwards user to the /admin/users.jsp
 * 
 * If user is not authenticated then forwards
 * user to the /login.jsp
 * 
 * If user is authenticated but doesn't have the 
 * admin role forwards user to /unauthorized.jsp
 * 
 * Uses JSecurity to determine if user is logged in
 * or not
 * 
 *
 */
public class GetAllUsers extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    static final long serialVersionUID = 1L;
   
    public GetAllUsers() {
        super();
    }   
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }  
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the user associated with this request
        //see: http://jsecurity.org/api/index.html?org/jsecurity/subject/Subject.html
        Subject currentUser = SecurityUtils.getSubject();
        String url = "/login.xhtml";
        
		if (currentUser.isAuthenticated() && ! currentUser.hasRole("admin") ) {
			//user is authenticated but doesn't have a role that
			//allows her to access this feature
			url = "/unauthorized.xhtml";
		}
		
		//check if the user is logged in
		//and has a role of admin
		if (currentUser.isAuthenticated() && currentUser.hasRole("admin") ) {
			//user is logged in 
			//get users from data store
			//and forward to /admin/users.jsp
                    url = "/admin/users.xhtml";
                    List<User> userList = UserDAO.getAllUsers();
                    request.setAttribute("userList", userList);
		}

        request.setAttribute("foo", "foo test");
        // forward the request and response to the view
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);   
    }         
}