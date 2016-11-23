package mjb44.mvc;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

public enum JSPMapping {
    LOGIN   ("login"    , "login.jsp"   ),
    EXAMPLE1("example-1", "example1.jsp"),
    EXAMPLE2("example-2", "example2.jsp"),
    BADLOGIN(null       , "badLogin.jsp"),
    LOGOUT  ("logout"   , "logout.jsp"  );    

    private String  path;
    private String  page;

    private JSPMapping(String path, String page) {
        this.path   = path;
        this.page   = page;
    }
    public String getPage() {
        return this.page;
    }
    public String getPath() {
        return this.path;
    }
    public String expectedRequestURI(HttpServletRequest req) {
        String s = String.format("%s/%s/%s"
                                 , req.getContextPath()
                                 , DDConstants.JSP_SECURE_PATH
                                 , this.getPage());
        return s;
    }
    
    public String expectedRequestControlURI(HttpServletRequest req) {
        String s = String.format("%s/%s/%s"
                                 , req.getContextPath()
                                 , DDConstants.SERVLET_PATH
                                 , this.getPath());
        System.out.printf("Expected Control path is: [%s]\n", s);
        return s;
    }

    public boolean reqIsForThisURI(HttpServletRequest req) {
        return req.getRequestURI().equals(expectedRequestURI(req));
    }
    public boolean reqIsForThisControlURI(HttpServletRequest req) {
        return req.getRequestURI().equals(expectedRequestControlURI(req));
    }
    
    public static JSPMapping fromPath(String s) {
        for (JSPMapping mapping: JSPMapping.values())
            if (Objects.equals(String.format("/%s", mapping.getPath()), s))
                return mapping;
        return null;
    }

    @Override
    public String toString() {
        return this.getPath();
    }
    
}
