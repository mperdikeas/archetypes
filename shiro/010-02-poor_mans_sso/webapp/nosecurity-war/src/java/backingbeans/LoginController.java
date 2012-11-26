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
import javax.faces.application.NavigationHandler;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedProperty;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.shiro.realm.jdbc.JdbcRealm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import mutil.jdbc.JdbcUtils;

import gr.neuropublic.jsf.util.JsfUtil;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    private static final Logger l = LoggerFactory.getLogger(LoginController.class);

    private String dataSourceURI;
    public void setDataSourceURI(String dataSourceURI) {
        this.dataSourceURI = dataSourceURI;
    }

    private String landingPage;
    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    @PostConstruct
    public void ensureConfigured() {
        if (this.dataSourceURI  == null) throw new RuntimeException("dataSource not configured");
        if (this.landingPage    == null) throw new RuntimeException("landingPage not configured");
    }

    private String message;
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getLoginUserName() {
        return username;
    }

    private String nickname;
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public void ensureNickname() throws NamingException, SQLException {
        if (username==null) throw new RuntimeException();
        InitialContext ic = new InitialContext();
        // String dataSourceURL = this.dataSource // "java:/usermgmnt";
        l.debug("looking for the '{}' datasource", dataSourceURI);
        DataSource dataSource = (DataSource) ic.lookup(dataSourceURI);
        l.debug("data source is: "+dataSource);
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT a.user_nickname FROM ss_users a WHERE a.user_email=?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            boolean haveBeenHereBefore = false;
            String nickName = null;
            while (rs.next()) {
                if (haveBeenHereBefore) throw new RuntimeException(username);
                nickname = rs.getString(1);
                haveBeenHereBefore = true;
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
    }

    private void loginInternal() throws UnknownAccountException, IncorrectCredentialsException, NamingException, SQLException {
	UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword());
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            l.info("subject is not authenticated, logging in");
            subject.login(token);
            token.clear();
            ensureNickname();
        }
        else
            l.info("subject is authenticated, not logging in again");
    }

    public void loginAuto() throws UnknownAccountException, IncorrectCredentialsException, NamingException, SQLException {
        l.info("loginAuto() called with username={}, pwd={}", getUsername(), getPassword());
        loginInternal();
        navigateToLandingPage();
    }

    private void navigateToLandingPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(context, null, String.format("%s?faces-redirect=true", landingPage));
    }

    public String login() {
	UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword());
	try {
            loginInternal();
	} catch (UnknownAccountException ex) {
            setMessage("Ο λογαριασμός δεν υπάρχει - προσπαθήστε ξανά");
            return null;
	} catch (IncorrectCredentialsException ex) {
            setMessage("Λάθος συνθηματικό - προσπαθήστε ξανά");
            return null;
	}
	catch (Exception ex) {
		ex.printStackTrace();
                throw new RuntimeException(); // panic
	}
        return JsfUtil.navigate(landingPage);
    }

    public void redirectIfAlreadyLoggedIn() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            try { navigateToLandingPage(); } catch (java.lang.IllegalStateException ignoreMe) {} // innocuous redirection: this is to implement the logic that if an authenticated subject revisits the login.xhtml view, he should be directed immediately to the landing page.
    }

    public String logout() {
	Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        final boolean containerSessions = false;
        if (containerSessions)  {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if( session != null ) {
                session.invalidate();
            }        
        }
        return JsfUtil.navigate("/login"); 
    }

    public boolean isPermitted(String permission) {
        //l.info("**************************************************************** isPermitted('{}')", permission);
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(permission);
    }

    public void checkPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission(permission);
    }
}
