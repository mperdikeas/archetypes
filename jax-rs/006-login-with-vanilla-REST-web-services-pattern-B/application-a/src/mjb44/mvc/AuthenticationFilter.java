package mjb44.mvc;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }


    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest  req  = (HttpServletRequest ) _req;
        System.out.printf("FFFF %s triggered, request URI is [%s]\n"
                          , this.getClass().getSimpleName()
                          , req.getRequestURI());
        HttpServletResponse res  = (HttpServletResponse) _res;
        final boolean PREVENT_USER_FROM_CHEATING_AFTER_LOGOUT_BY_USING_THE_BACK_BUTTON = true;
        if (PREVENT_USER_FROM_CHEATING_AFTER_LOGOUT_BY_USING_THE_BACK_BUTTON)
            setNoCacheHeaders(_res);
        HttpSession session = req.getSession(false);
        boolean loginRequest = JSPMapping.LOGIN.reqIsForThisControlURI(req);
            
        boolean loggedIn = (session!=null) && (SessionScopeAttributes.get(session, SessionScopeAttributes.USER, Object.class)!=null);
        if (loggedIn || loginRequest) {
            chain.doFilter(_req, _res);
        } else {
            session = req.getSession(true);
            SessionScopeAttributes.set(session
                                       , SessionScopeAttributes.HACK_ATTEMPT
                                       , new HackInfo(req.getRemoteAddr()
                                                      , req.getHeader("User-Agent")));
            sendToLogin(req, res);
        }
    }

    private static void sendToLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String urlForRedirect = String.format("%s://%s:%s%s/%s/%s"
                                              , req.getScheme()
                                              , req.getServerName()
                                              , req.getServerPort()
                                              , req.getContextPath()
                                              , DDConstants.SERVLET_PATH
                                              , JSPMapping.LOGIN.getPath());
        res.sendRedirect(urlForRedirect);
    }

    private static void setNoCacheHeaders(ServletResponse _res) {
        HttpServletResponse res = (HttpServletResponse) _res;
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0);
    }

    @Override
    public void destroy() {
        //
    }
}
