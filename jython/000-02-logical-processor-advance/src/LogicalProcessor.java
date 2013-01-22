import java.util.Map;

import org.python.core.PyException;

import mutil.python.PythonExposer;
import mutil.base.Triad;

public class LogicalProcessor {

    private static String RET_VALUE = "RETURNVALUE";

    private static String indent(String script) {
        return script.replace("\n", "\n\t");        
    }
    private static String adorned(String script) {
        return "def foo():\n\t"+indent(script)+String.format("\n%s=foo()", RET_VALUE);
    }

    public static Boolean[] evaluate(String preamble, String[] scripts) {
        Boolean[] retValue = new Boolean[scripts.length];
        int i = 0;
        for (String script : scripts) {
            // System.out.println("evaluating: "+script);
            Triad<Map<String, Object>, String, PyException> eval =
                PythonExposer.exposeParams(null, null, ((preamble==null)?"":preamble+"\n")+adorned(scripts[i]));
            Map<String, Object> values = eval.a;
            retValue[i++] = (Boolean) values.get(RET_VALUE);
        }
        return retValue;
    }

}