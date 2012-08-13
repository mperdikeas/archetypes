package controllers;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import java.awt.event.ActionEvent;

import entities.User;
import facades.IUserFacadeRemote;
import facades.IUserFacadeLocal;

import mutil.base.ExceptionAdapter;

@ManagedBean(name="indexBackingBean")
@SessionScoped
public class IndexBackingBean implements Serializable {
    private static final Logger l = Logger.getLogger(IndexBackingBean.class.getName());
    private static final String CLASS_NAME = IndexBackingBean.class.getName();

    // @EJB(beanName="userFacade") // EJB3 injection refid:234lkdjs0923kljhdfsf082
    private IUserFacadeRemote userFacade;

    private IUserFacadeRemote getUserFacade() {
        if (userFacade == null) {
            String jndiName = "java:global/Users-ejb/userFacade!facades.IUserFacadeRemote";
            try {
                this.userFacade = (IUserFacadeRemote) new InitialContext().lookup(jndiName); 
            } catch (Exception e) {
                throw new ExceptionAdapter(e);
            }
        }
        return userFacade;
    }

    private String name;
    public String getName() {
        l.info(CLASS_NAME+"#getName() returning: "+name);
        return name;
    }
    public void setName(String name) {
        l.info(CLASS_NAME+"#setName("+name+")");
        this.name = name;
    }

    public String jdbcUrl;
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String retrieveJdbcUrl() {
        l.info("inside "+CLASS_NAME+"#retrieveJdbcUrl(): name="+name);
        User user = getUserFacade().find(name);
        if (user==null) {
            jdbcUrl="not found";
            return "null";
        }
        else {
            jdbcUrl = user.getJdbcCoords();
            l.info("setting jdbcUrl to the value of: "+jdbcUrl);
            return "PrivateUserData";
        }
    }
}