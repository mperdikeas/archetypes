import mutil.base.ExceptionAdapter;


import org.apache.commons.lang3.StringEscapeUtils;

public class JythonUtils {


    public String escape(String foo) {
        return StringEscapeUtils.escapeJava(foo);
    }

    public void println(String foo) {
        System.out.println(foo);
    }

    public void print(String foo) {
        System.out.print(foo);
    }

}