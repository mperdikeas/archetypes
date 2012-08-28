package backingbeans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedProperty;
import java.util.logging.Logger;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.ComponentSystemEvent;
import java.util.Collection;
import java.util.Date;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;


@ManagedBean
@RequestScoped
public class LoginController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String CLASSNAME=LoginController.class.getName();


    private String username;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    private String password;
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
		String url = "/login.jsp";
		
		UsernamePasswordToken token = 
                    new UsernamePasswordToken(getUsername(), getPassword());
	
		try {
			
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			
			token.clear();

                        if ( subject.hasRole("admin") ) System.out.println(subject.getPrincipal() + " has admin role");
                        else                            System.out.println(subject.getPrincipal() +  " doesn't have admin role");
                        if (subject.hasRole("user"))    System.out.println(subject.getPrincipal() + " has user role");
                        else                            System.out.println(subject.getPrincipal() + " doesn't have user role");
                        if (subject.hasRole("staff"))   System.out.println(subject.getPrincipal() + " has staff role");
                        else                            System.out.println(subject.getPrincipal() + " doesn't have staff role");
                        
                        if (subject.isPermitted("secure")) System.out.println(subject.getPrincipal()+" has the 'secure' permission");
                        if       (subject.isAuthenticated() && subject.isPermitted("secure"))  url = "/staff/index.jsp";
                        else if  (subject.isAuthenticated() && subject.hasRole    ("user"  ))  url = "/user/index.jsp";
                        else if  (subject.isAuthenticated() && subject.hasRole    ("admin" ))  url = "/admin/index.jsp";
                        else                                                                   url = "/unauthorized.jsp";

		} catch (UnknownAccountException ex) {
			//username provided was not found
			ex.printStackTrace();
			// request.setAttribute("error", ex.getMessage() );
			
		} catch (IncorrectCredentialsException ex) {
			//password provided did not match password found in database
			//for the username provided
			ex.printStackTrace();
			// request.setAttribute("error", ex.getMessage());
		}
		
		catch (Exception ex) {
			
			ex.printStackTrace();
			
			// request.setAttribute("error", "Login NOT SUCCESSFUL - cause not known!");
			
		}
		
		
                return url;
	

    }

}
