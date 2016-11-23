package mjb44.mvc;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Enumeration;
import java.util.Random;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        process(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        process(req, res);
    }
    
    public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        sanityCheckServletPath(req);
        HttpSession session = req.getSession(true); // create the session if it does not exist
        String effectivePath = effectivePath(req);
        System.out.printf("CCCC %s triggered, effective path is: [%s] \n"
                          , this.getClass().getName()
                          , effectivePath);
        JSPMapping jspMapping = JSPMapping.fromPath(effectivePath);
        if (jspMapping==null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        switch (jspMapping) {
        case LOGIN: {
            if ("POST".equals(req.getMethod())) {
                assertUserIsNotLoggedIn(req);
                DAO dao = getDAOorCreateOneIfDoesNotExist(getServletContext());            
                String username = (String) req.getParameter("username");
                String password = (String) req.getParameter("password");
                LoginBean loginBean = dao.checkCredentials(username, password);
                if (loginBean != null) {
                    SessionScopeAttributes.set(session, SessionScopeAttributes.USER, loginBean);
                    DispatcherUtil.sendToJsp(req, res, JSPMapping.EXAMPLE1);
                } else {
                    SessionScopeAttributes.set(session, SessionScopeAttributes.BAD_USER, username);
                    DispatcherUtil.sendToJsp(req, res, JSPMapping.BADLOGIN);
                }
            } else if ("GET".equals(req.getMethod())) {
                boolean loggedIn = (SessionScopeAttributes.get(session, SessionScopeAttributes.USER, Object.class)!=null);
                if (!loggedIn) {
                    boolean hackAttempt = (SessionScopeAttributes.get(session, SessionScopeAttributes.HACK_ATTEMPT, Object.class)!=null);
                    if (hackAttempt) {
                        // we may now remove it from session (where it had to be placed due to the redirect) and put it in request scope
                        HackInfo hackInfo = SessionScopeAttributes.get(session, SessionScopeAttributes.HACK_ATTEMPT, HackInfo.class);
                        SessionScopeAttributes.remove(session, SessionScopeAttributes.HACK_ATTEMPT);
                        RequestScopeAttributes.set(req, RequestScopeAttributes.HACK_ATTEMPT, hackInfo);
                    }
                    DispatcherUtil.sendToJsp(req, res, JSPMapping.LOGIN);
                } else {
                    RequestScopeAttributes.set(req, RequestScopeAttributes.ALREADY_LOGGEDIN_REDIRECT, true);
                    DispatcherUtil.sendToJsp(req, res, JSPMapping.EXAMPLE1);
                }
            } else throw new RuntimeException(String.format("Unhandled method: [%s]"
                                                            , req.getMethod()));
            break;
        }
        case EXAMPLE1: {
            assertRequestMethodIs(req, "GET");
            DispatcherUtil.sendToJsp(req, res, JSPMapping.EXAMPLE1);
            break;
        }
        case EXAMPLE2: {
            assertRequestMethodIs(req, "GET");
            DispatcherUtil.sendToJsp(req, res, JSPMapping.EXAMPLE2);
            break;
        }
        case LOGOUT: {
            if ("POST".equals(req.getMethod())) {
                session.invalidate();
                DispatcherUtil.sendToJsp(req, res, JSPMapping.LOGIN);
            } else if ("GET".equals(req.getMethod())) {
                DispatcherUtil.sendToJsp(req, res, JSPMapping.LOGOUT);
            } else
                throw new RuntimeException(String.format("Unsupported method (%s) for path: [%s]"
                                                         , req.getMethod()
                                                         , JSPMapping.LOGOUT.getPath()));
            break;
        }                            
        default:
            throw new RuntimeException(String.format("Unhandled JSPMapping enum: [%s] generated from effective path: [%s]"
                                                     , jspMapping.toString()
                                                     , effectivePath));

        }
    }

    private static void assertUserIsNotLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session==null)
            return;
        else {
            LoginBean loginBean = SessionScopeAttributes.get(session, SessionScopeAttributes.USER, LoginBean.class);
            if (loginBean!=null)
                throw new RuntimeException(loginBean.toString());
        }
    }


    private static String effectivePath(HttpServletRequest req) {
        sanityCheckServletPath(req);
        return req.getRequestURI().substring(req.getContextPath().length()
                                             + req.getServletPath().length());
    }

    
    private static DAO getDAOorCreateOneIfDoesNotExist(ServletContext servletContext) {
        DAO dao = ApplicationScopeAttributes.get(servletContext
                                                 , ApplicationScopeAttributes.DAO
                                                 , DAO.class);
        if (dao == null) { // not thread-safe, I know ...
            ApplicationScopeAttributes.set(servletContext
                                           , ApplicationScopeAttributes.DAO
                                           , new DAO());
            dao = ApplicationScopeAttributes.get(servletContext
                                                 , ApplicationScopeAttributes.DAO
                                                 , DAO.class);
        }
        return dao;
    }

    private static void assertRequestMethodIs(HttpServletRequest req, String expected) {
        if (!expected.equals(req.getMethod())) throw new RuntimeException(String.format("Unexpected method encountered: [%s] - was expecting it to be: [%s]"
                                                                                        , req.getMethod()
                                                                                        , expected));
    }


    private static void sanityCheckServletPath(HttpServletRequest req) {
        final String EXPECTED = "".equals(DDConstants.SERVLET_PATH)
            ?""
            :String.format("/%s", DDConstants.SERVLET_PATH);
        if (!req.getServletPath().equals(EXPECTED))
            throw new RuntimeException(String.format("Unexpected servlet path: [%s] - was expecting it to be [%s]"
                                                     , req.getServletPath()
                                                     , EXPECTED));
    }
    
}
