package mjb44.calculator_with_navigation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.faces.application.ResourceHandler;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {


    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURL       = request.getContextPath() + "/login.xhtml";
        String failedLoginURL = request.getContextPath() + "/failed-login.xhtml";
        boolean loggedIn = (session != null) && (session.getAttribute(SessionKeys.LOGGEDIN_USER) != null);
        boolean loginRequest   = request.getRequestURI().equals(loginURL);
        boolean failedLogin    = request.getRequestURI().equals(failedLoginURL);
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(request.getHeader("Faces-Request"));
        System.out.printf("\nLLLLLLLLLLLLLLLLLLLLLLLLL\n loggedInUser=[%s], loginRequest=[%b], failedLogin=[%b], resourceRequest=[%b], ajaxRequest=[%b]\n"
                          , ((session==null) || ((session != null) && (session.getAttribute(SessionKeys.LOGGEDIN_USER)==null)))?"N/A":session.getAttribute(SessionKeys.LOGGEDIN_USER)
                          , loginRequest
                          , failedLogin
                          , resourceRequest
                          , ajaxRequest);
        if (loggedIn || loginRequest || failedLogin || resourceRequest) {
            if (!resourceRequest) { // Prevent browser from caching restricted resources. See also http://stackoverflow.com/q/4194207/157882
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                response.setDateHeader("Expires", 0); // Proxies.
            }

            chain.doFilter(request, response); // So, just continue request.
        }
        else if (ajaxRequest) {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().printf(AJAX_REDIRECT_XML, loginURL); // So, return special XML response instructing JSF ajax to send a redirect.
        }
        else {
            response.sendRedirect(loginURL); // So, just perform standard synchronous redirect.
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }
}
