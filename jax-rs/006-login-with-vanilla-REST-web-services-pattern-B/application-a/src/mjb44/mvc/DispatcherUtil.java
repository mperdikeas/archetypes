package mjb44.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DispatcherUtil {

    public static void sendToJsp(HttpServletRequest req, HttpServletResponse res, JSPMapping jspMapping) throws ServletException, IOException {

        // using the mechanism of keeping the JSP pages in web/WEB-INF
        // a redirect is not possible and our only option is to use
        // the forwarding mechanism.
        boolean sendUsingRedirect = false;
        if (sendUsingRedirect) {
            String urlForRedirect = String.format("%s://%s:%s%s/%s/%s"
                                                  , req.getScheme()
                                                  , req.getServerName()
                                                  , req.getServerPort()
                                                  , req.getContextPath()
                                                  , DDConstants.JSP_SECURE_PATH
                                                  , jspMapping.getPage());
            res.sendRedirect(urlForRedirect);
        } else {
            String urlForForward =  String.format("/%s/%s"
                                                  , DDConstants.JSP_SECURE_PATH
                                                  , jspMapping.getPage());
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(urlForForward);            
            dispatcher.forward(req, res);            
        }
    }

}
