import mutil.base.ExceptionAdapter;


import org.apache.commons.lang3.StringEscapeUtils;

public class JythonUtils {


    public String escape(String foo) {
        return StringEscapeUtils.escapeJava(foo);
    }

}