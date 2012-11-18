package gr.neuropublic.jsf.util;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import java.util.logging.Logger;


@FacesConverter("gr.neuropublic.jsf.util.converters.TriStateCheckboxToBooleanConverter")
public class TriStateCheckboxToBooleanConverter implements Converter {

    private final Logger l = Logger.getLogger(this.getClass().getName());

    public static final String ZERO = "0";
    public static final String  ONE = "1";
    public static final String  TWO = "2";

    public static final boolean nullOrEmptyString(String s) {
        if      (s==null            ) return true;
        else if (s.trim().equals("")) return true;
        else                          return false;
    }

    private static final String safeString(String s) {
        if (s==null) return "NULL"; else return s;
    }
    private static final String safeBoolean(Boolean b) {
        if (b==null) return "NULL"; else return b.toString();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        l.info(" converting value of: "+safeString(value)+" to Boolean");
        return getBoolean(value);
    }
    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        String retValue;
        l.info("inside TriStateCheckboxToBooleanConverter getAsString");
        if      (  value == null)          retValue = TWO;
        else if (  ((Boolean) value) == Boolean.TRUE)  retValue = ONE;
        else if (  ((Boolean) value) == Boolean.FALSE) retValue = ZERO;
        else throw new RuntimeException();
        l.info("value to be returned is: "+(retValue==null?"NULL":retValue));
        return retValue;
    }
    
    public static Boolean getBoolean(String value) {
        Boolean retValue = null;
        if      (nullOrEmptyString(value)) retValue = null;
        else if (value.equals(ZERO)      ) retValue = new Boolean(Boolean.FALSE);
        else if (value.equals( ONE)      ) retValue = new Boolean(Boolean.TRUE);
        else if (value.equals( TWO)      ) retValue = null;
        else                               throw new RuntimeException();
        return retValue;
    }
}
