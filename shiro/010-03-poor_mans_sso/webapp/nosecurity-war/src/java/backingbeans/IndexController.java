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

import javax.servlet.http.HttpServletResponse;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletException;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class IndexController implements Serializable {

    private static final Logger l = LoggerFactory.getLogger(IndexController.class);    

    @ManagedProperty(value="#{loginController}")
    LoginController loginController;
    public LoginController getLoginController()                     { return loginController; }
    public void setLoginController(LoginController loginController) { this.loginController = loginController; }


    public void otherApp(String where) throws ServletException, java.io.IOException {
        l.info(String.format("otherApp("+where+") called. user,pwd=%s,%s",
                                    loginController.getUsername(),
                             loginController.getPassword()));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String redirectURL = String.format("http://%s?user=%s&pwd=%s" ,where ,loginController.getUsername()
                                           ,loginController.getPassword());
        l.info("redirecting to: "+redirectURL);
        ec.redirect(redirectURL);
    }
}
