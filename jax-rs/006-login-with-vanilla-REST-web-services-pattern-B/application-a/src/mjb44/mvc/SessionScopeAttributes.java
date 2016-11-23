package mjb44.mvc;

import javax.servlet.http.HttpSession;

public enum SessionScopeAttributes {
    USER("loggedInUser"), BAD_USER("badUser"), HACK_ATTEMPT("hackAttempt");

    private SessionScopeAttributes(String value) {
        this.value = value;
    }
    
    private String value;
    public  String getValue() {
        return this.value;
    }

    public static <T> T get(HttpSession session, SessionScopeAttributes attr, Class<T> klass) {
        return (T) session.getAttribute(attr.getValue());
    }

    public static void set(HttpSession session, SessionScopeAttributes attr, Object o) {
        session.setAttribute(attr.getValue(), o);
    }
    public static void remove(HttpSession session, SessionScopeAttributes attr) {
        session.removeAttribute(attr.getValue());
    }
}
