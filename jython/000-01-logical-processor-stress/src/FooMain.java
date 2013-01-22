import java.util.Map;

import org.python.core.PyException;

import mutil.python.PythonExposer;
import mutil.base.Triad;

public class FooMain {

    private static String RET_VALUE = "RETURNVALUE";

    public static String[] randomScripts(int n) {
        String[] retValue = new String[n];
        for (int i = 0 ; i < n ; i++)
            retValue[i]=String.format("return %s", System.currentTimeMillis()%2==0?"True":"False");
        return retValue;
    }

    public static String indent(String script) {
        return script.replace("\n", "\n\t");        
    }
    public static String adorned(String script) {
        return "def foo():\n\t"+indent(script)+String.format("\n%s=foo()", RET_VALUE);
    }

    public static void main(String args[]) {
        final int N = 10000;
        String[] scripts = randomScripts(N);
        int ntrue  = 0;
        for (int i = 0 ; i < N ; i++) {
            //System.out.println(String.format("evaluating: '%s'", adorned(script)));
            Triad<Map<String, Object>, String, PyException> eval =
                PythonExposer.exposeParams(null, null, adorned(scripts[i]));
            Map<String, Object> values = eval.a;
            boolean valueObtained = (Boolean) values.get(RET_VALUE);
            if (valueObtained) ntrue++;
        }
        System.out.println(String.format("precentage of true values: %5.3f (%d out of %d)", ((float)ntrue)/N, ntrue, N));
    }
}