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



    @EJB(mappedName = "java:global/Users-ejb/Users-ejb/userFacade!facades.IUserFacadeRemote")
    private IUserFacadeRemote userFacade;

    static int _i = 0;
    private IUserFacadeRemote getUserFacade() {
        if ((_i++ % 2)==3) {
            l.info("getting facade via JNDI lookup");
            return getUserFacade_via_JNDI();
        } else {
            l.info("gettting facade via injection");
            return getUserFacade_via_Injection();
        }
    }

    private IUserFacadeRemote getUserFacade_via_JNDI() {
        String jndiName = "java:global/Users-ejb/Users-ejb/userFacade!facades.IUserFacadeRemote";
        try {
            // we don't cache the returned value so we look ip up every time
            return (IUserFacadeRemote) new InitialContext().lookup(jndiName); 
        } catch (Exception e) {
            throw new ExceptionAdapter(e);
        }
    }

    private IUserFacadeRemote getUserFacade_via_Injection() {
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
        IUserFacadeRemote userFacade = getUserFacade();
        l.info("boo msg is: "+userFacade.boo("Guy Fawkes"));
        l.info("userFacade is: "+userFacade);
        User user = userFacade.find(name);
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