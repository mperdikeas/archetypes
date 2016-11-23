package mjb44.mvc;

import javax.servlet.ServletContext;

public enum ApplicationScopeAttributes {
    DAO("dao");

    private ApplicationScopeAttributes(String value) {
        this.value = value;
    }
    
    private String value;
    public  String getValue() {
        return this.value;
    }

    public static <T> T get(ServletContext servletContext, ApplicationScopeAttributes attr, Class<T> klass) {
        return (T) servletContext.getAttribute(attr.getValue());
    }

    public static void set(ServletContext servletContext, ApplicationScopeAttributes attr, Object o) {
        servletContext.setAttribute(attr.getValue(), o);
    }
}
