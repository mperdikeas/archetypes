package a.b.c;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextListenerImpl implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute(Constants.RANDOM_GENERATOR, new SessionIdentifierGenerator());        
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

}
