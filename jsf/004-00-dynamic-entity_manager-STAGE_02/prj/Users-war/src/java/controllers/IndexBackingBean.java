package controllers;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import java.awt.event.ActionEvent;

import entities.User;
import facades.UserFacade;

@ManagedBean(name="indexBackingBean")
@SessionScoped
public class IndexBackingBean implements Serializable {
    private static final Logger l = Logger.getLogger(IndexBackingBean.class.getName());
    private static final String CLASS_NAME = IndexBackingBean.class.getName();

    @EJB
    private UserFacade userFacade;

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
        User user = userFacade.find(name);
        if (user==null) {
            jdbcUrl="not found";
            return "null";
        }
        else {
            jdbcUrl = user.getJdbcCoords();
            return "PrivateUserData";
        }
    }
}