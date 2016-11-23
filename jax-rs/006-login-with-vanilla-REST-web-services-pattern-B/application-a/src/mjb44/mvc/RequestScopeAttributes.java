package mjb44.mvc;

import javax.servlet.http.HttpServletRequest;

public enum RequestScopeAttributes {
    ALREADY_LOGGEDIN_REDIRECT("alreadyLoggedInRedirect"),
    HACK_ATTEMPT(SessionScopeAttributes.HACK_ATTEMPT.getValue());

    private RequestScopeAttributes(String value) {
        this.value = value;
    }
    
    private String value;
    public  String getValue() {
        return this.value;
    }

    public static <T> T get(HttpServletRequest request, RequestScopeAttributes attr, Class<T> klass) {
        return (T) request.getAttribute(attr.getValue());
    }

    public static void set(HttpServletRequest request, RequestScopeAttributes attr, Object o) {
        request.setAttribute(attr.getValue(), o);
    }
}
