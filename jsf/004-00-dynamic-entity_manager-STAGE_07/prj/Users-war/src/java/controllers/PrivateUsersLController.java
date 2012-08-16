package controllers;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
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

import mutil.base.Pair;
import mutil.base.Triad;
import mutil.base.ExceptionAdapter;

import entities.*;
import facades.*;


@ManagedBean
@RequestScoped
public class PrivateUsersLController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());

    @ManagedProperty(value="#{indexBackingBean}")
    private IndexBackingBean indexBackingBean;

    public void setIndexBackingBean(IndexBackingBean indexBackingBean) {
        this.indexBackingBean = indexBackingBean;
    }

    private PrivateUserData current;
    private List<PrivateUserData> items;
    public PrivateUserData getCurrent() {return current;}
    public void setCurrent(PrivateUserData current) {
        this.current = current;
    }

    public PrivateUsersLController() {
    }

    public static String safePrivateUserDataToString(PrivateUserData x) {
        return x==null?"null":(x.getEmployee()+":"+x.getEmployeePersonal());
    }

    public static String safeListSizeToString(List<?> aList) {
        return aList==null?"list is null":aList.size()+" elements";
    }


    public List<PrivateUserData> getItems() {
        if (items == null) {
            l.info("inside "+PrivateUsersLController.class.getName()+"#getItems()");            
            items = getFacade().findAll();
            l.info("**************** "+items.size()+" items returned");
        }
        return items;
    }

    private static int _i = 0 ;

    private String ejbName() {
        final String appName         = indexBackingBean.getJdbcUrl(); // "Users-ejb"; // the ear package
        final String moduleName      = "Users-ejb"; // the jar package
        final String distinctName    = "";
        final String beanName        = "PrivateUserDataFacade"; // The EJB name which by default is the simple
                                                                    // class name of the bean implementation class
        final String viewClassName = IPrivateUserDataFacade.class.getName();  // the remote view fully qualified class name
        final boolean stateful=false;
            
        String ejbName = "ejb:" + appName + "/"  + moduleName + "/" + distinctName + "/" 
               + beanName + "!" + viewClassName    + (stateful?"?stateful":"");
        return ejbName;
    }

    private IPrivateUserDataFacade getFacade() {
        // return privateUserDataFacade; // injection case ref-id: 2l3kj4lsd092
        // if (privateUserDataFacade == null) { // don't store it, always get a new one to test
                                                // different kinds of names. refid:234sdf089u23kljhdfskjh

        /* The JBoss AS reports the following names: 

           java:global/Users-ejb/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:app/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:module/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:jboss/exported/Users-ejb/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:global/Users-ejb/Users-ejb/PrivateUserDataFacade
           java:app/Users-ejb/PrivateUserDataFacade
           java:module/PrivateUserDataFacade

           --
           I suppose I can use only those in the java:global namespace since the war
           is deployed separately from the ear
        */
        String ejbName = ejbName();
        String jndiNames[] = {
            "java:global/"+indexBackingBean.getJdbcUrl()+"/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:app/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:module/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:jboss/exported/"+indexBackingBean.getJdbcUrl()+"/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:global/"+indexBackingBean.getJdbcUrl()+"/Users-ejb/PrivateUserDataFacade",
            "java:app/Users-ejb/PrivateUserDataFacade",
            "java:module/PrivateUserDataFacade",
             ejbName};
        String jndiName = jndiNames[_i++ % jndiNames.length];
        try {
            l.info("**************** "+_i+" of "+jndiNames.length+" using jndiName: "+jndiName);
            IPrivateUserDataFacade privateUserDataFacade = (IPrivateUserDataFacade) new InitialContext().lookup(jndiName);
            return privateUserDataFacade;
        } catch (Exception e) {
            l.info("failed with name: "+jndiName+" with message: "+e.getMessage()+", trying with other name");
            return getFacade();
        }
        /*        }
        return privateUserDataFacade; */ // refid:234sdf089u23kljhdfskjh
    }

}
