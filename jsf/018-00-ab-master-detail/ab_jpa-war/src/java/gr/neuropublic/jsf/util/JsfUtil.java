package gr.neuropublic.jsf.util;



import java.text.MessageFormat;
import java.util.*;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;

public class JsfUtil {

    private static final String APPLICATION_FACTORY_KEY =
            "javax.faces.application.ApplicationFactory";
    
    private static final String BUNDLE_NAME = "/Bundle";

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addErrorMessage(String msg, String msgDtl) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msgDtl);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    /**
     * Convenience method for setting Session variables.
     *
     * @param key object key
     * @param object value to store
     */
    public static void storeOnSession(String key, Object object) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map session = ctx.getExternalContext().getSessionMap();
        session.put(key, object);
    }

    /**
     * Convenience method for getting Session variables.
     *
     * @param key object key
     */
    public static Object getFromSession(String key) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map session = ctx.getExternalContext().getSessionMap();
        return session.get(key);
    }

    /**
     * Convenience method for setting Request attributes.
     *
     * @param key object key
     * @param object value to store
     */
    public static void storeOnRequest(String key, Object object) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map request = ctx.getExternalContext().getRequestMap();
        request.put(key, object);
    }

    /**
     * Convenience method for getting Request attributes.
     *
     * @param key object key
     */
    public static Object getFromRequest(String key) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map request = ctx.getExternalContext().getRequestMap();
        return request.get(key);
    }

    /**
     * Evaluates JSF EL expression and returns the value.
     *
     * @param jsfExpression
     * @return
     */
    public static Object getExpressionValue(String jsfExpression) {
        // when specifying EL expression in managed bean as "literal" value
        // so t can be evaluated later, the # is replaced with $, quite strange
        if (jsfExpression == null) {
            return jsfExpression;
        }
        if (jsfExpression.startsWith("${")) {
            jsfExpression = "#{" + jsfExpression.substring(2);
        }
        if (!jsfExpression.startsWith("#{")) {
            if (jsfExpression.equalsIgnoreCase("true")) {
                return Boolean.TRUE;
            } else if (jsfExpression.equalsIgnoreCase("false")) {
                return Boolean.FALSE;
            } // there can be literal text preceding the expression...
            else if (jsfExpression.indexOf("#{") < 0) {
                return jsfExpression;
            }
        }
        return getApplication().createValueBinding(jsfExpression).getValue(FacesContext.getCurrentInstance());
    }
    
    /**
     * Access Managed Bean from Faces Context passing the name of the bean
     * 
     * @param beanName
     * @return 
     */
    public static Object accessBeanFromFacesContext(String beanName) {
        Object beanObject = FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, beanName);
        return beanObject;
    }

    /**
     *
     * @return
     */
    public static Application getApplication() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            return FacesContext.getCurrentInstance().getApplication();
        } else {
            ApplicationFactory afactory =
                    (ApplicationFactory) FactoryFinder.getFactory(APPLICATION_FACTORY_KEY);
            return afactory.getApplication();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static Locale getLocale() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    /**
     * Retrieves the message resource string by passing the message key
     * 
     * @param key
     * @return 
     */
    public static String getMsgString(String key) {
        return getMsgString(key, null);
    }
    
    /**
     * Retrieves the message resource string by passing the message key, and message parameters
     * 
     * ref: http://stackoverflow.com/questions/9390654/how-to-read-messages-and-pass-parameters-to-message-properties-file-in-jsf-2-0
     * 
     * @param key
     * @param params
     * @return 
     */
    public static String getMsgString(String key, Object params[]) {

        String text;
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, getLocale());

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? " + key + " ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(text, getLocale());
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }
    
    public static Object getRowData(UIComponent component) {
        return ((DataTable)getParentTable(component)).getRowData();
    }
    
    public static UIComponent getParentTable(UIComponent component) {
        UIComponent parent = component.getParent();
        if (parent instanceof DataTable) {
            return parent;
        } else {
            return getParentTable(parent);
        }
    }
}