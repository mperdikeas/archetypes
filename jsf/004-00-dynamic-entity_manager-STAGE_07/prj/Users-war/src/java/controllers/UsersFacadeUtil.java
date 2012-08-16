package controllers;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import java.util.logging.Logger;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Date;

import mutil.base.Pair;
import mutil.base.Triad;
import mutil.base.ExceptionAdapter;

import entities.*;
import facades.*;



public class UsersFacadeUtil {

    private static final Logger l = Logger.getLogger(UsersFacadeUtil.class.getName());

    private static int _i = 0 ;

    private static String ejbName(String appName1) {
        final String appName         = appName1;    // "Users-ejb"; // the ear package
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

    public static IPrivateUserDataFacade getFacade(String appName) {
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
        String ejbName = ejbName(appName);
        String jndiNames[] = {
            "java:global/"+appName+"/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:app/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:module/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:jboss/exported/"+appName+"/Users-ejb/PrivateUserDataFacade!facades.IPrivateUserDataFacade",
            "java:global/"+appName+"/Users-ejb/PrivateUserDataFacade",
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
            return getFacade(appName);
        }
     }

}