package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
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

    // @EJB // injection-case ref-id: 2l3kj4lsd092
    // private PrivateUserDataFacade privateUserDataFacade; // we don't store it so we retrieve it every time
                                                            // using a different JNDI name and so we can test
                                                            // different names. refid:234sdf089u23kljhdfskjh

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
            final String appName         = "Users";
            final String moduleName      = "Users-ejb";
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
           java:global/Users/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:app/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:module/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:jboss/exported/Users/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade
           java:global/Users/Users-ejb/PrivateUserDataFacade
           java:app/Users-ejb/PrivateUserDataFacade
           java:module/PrivateUserDataFacade
           --
           I suppose I can use any of these provided they reside in the java:app and java:global
           namespaces (I've tried the java:module namespace and it's not working)
        */
        String ejbName = ejbName();
        String jndiNames[] = {
            "java:global/Users/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:app/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:module/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:jboss/exported/Users/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:global/Users/Users-ejb/PrivateUserDataFacade",
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
