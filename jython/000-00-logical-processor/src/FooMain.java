import java.util.Map;

import org.python.core.PyException;

import mutil.python.PythonExposer;
import mutil.base.Triad;

public class FooMain {

    private static String RET_VALUE = "RETURNVALUE";

    public static String[] scripts = {
        "return True", "return False", "i=10\nj=12\nreturn (i==j)", "i=10\nj=10\nreturn (i==j)"
    };

    public static String indent(String script) {
        return script.replace("\n", "\n\t");        
    }
    public static String adorned(String script) {
        return "def foo():\n\t"+indent(script)+String.format("\n%s=foo()", RET_VALUE);
    }

    public static void main(String args[]) {
        System.out.println("foo");
        for (String script : scripts) {
            System.out.println(String.format("evaluating: '%s'", adorned(script)));
            Triad<Map<String, Object>, String, PyException> eval =
                PythonExposer.exposeParams(null, null, adorned(script));
            Map<String, Object> values = eval.a;
            System.out.println("obtained the value: "+values.get(RET_VALUE));
        }
    }
}