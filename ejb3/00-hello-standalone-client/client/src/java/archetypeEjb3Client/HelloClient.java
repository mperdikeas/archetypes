package archetypeEjb3Client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import archetypesEjb3.IHelloEJB3;

public class HelloClient {
    public static void main(String[] args) {

        Hashtable env = new Hashtable();
        env.put(Context.URL_PKG_PREFIXES         , "org.jboss.ejb.client.naming");

        try {
            final Context context = new InitialContext(env);

            // The app name is the application name of the deployed EJBs. This is typically the ear name
            // without the .ear suffix. However, the application name could be overridden in the application.xml of the
            // EJB deployment on the server.
            // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
            final String appName = "";
            // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
            // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
            // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
            // jboss-as-ejb-remote-app
            final String moduleName = "hello";
            // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
            // our EJB deployment, so this is an empty string
            final String distinctName = "";
            final String beanName = "HelloEJB3Bean"; // The EJB name which by default is the simple
                                                     // class name of the bean implementation class
            final String viewClassName = IHelloEJB3.class.getName();        // the remote view fully qualified class name
            final boolean stateful=false;
            
            String jndiName = "ejb:" + appName + "/"  + moduleName + "/" + distinctName + "/" 
                + beanName + "!" + viewClassName    + (stateful?"?stateful":"");

            String allJndiNames[] = {
                               jndiName,
                               "java:global/hello/HelloEJB3Bean!archetypesEjb3.IHelloEJB3",
                               "java:app/hello/HelloEJB3Bean!archetypesEjb3.IHelloEJB3",
                               "java:module/HelloEJB3Bean!archetypesEjb3.IHelloEJB3",
                               "java:jboss/exported/hello/HelloEJB3Bean!archetypesEjb3.IHelloEJB3",
                               "java:global/hello/HelloEJB3Bean",
                               "java:app/hello/HelloEJB3Bean",
                               "java:module/HelloEJB3Bean"
                                     } ;
            
            for (String aName : allJndiNames) {
                System.out.println("**************** looking up for: "+aName);
                try {
                    IHelloEJB3 helloUser = (IHelloEJB3) context.lookup(aName);
                    System.out.println("helloUser is: "+helloUser);
                    System.out.println(helloUser.sayMessage("Dilalah"));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(" <================ swalowing it");
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
