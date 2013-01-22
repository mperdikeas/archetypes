import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.python.core.PyException;

import mutil.python.PythonExposer;
import mutil.base.Triad;

public class FooMain {

    private static String preamble() {
        String[] foo = {
            "vat = '047787907'",
            "def hlikia():",
            "    if (vat=='047787907'):",
            "        return 39",
            "    else:",
            "        return 20"};
        return StringUtils.join(foo, "\n");
    }

    private static String falseScript() {
        return "return hlikia()==20";
    }

    private static String dunnoScript() {
        return "if hlikia()==40:\n  return True";
    }

    private static String trueScript() {
        return "return hlikia()!=20";
    }

    public static String[] randomScripts(int n) {
        String[] retValue = new String[n];
        int counter = 0 ;
        for (int i = 0 ; i < n ; i++) {
            String script = null;
            switch (counter++ % 3) {
                case 0: script = falseScript() ;break;
                case 1: script = dunnoScript() ;break;
                case 2: script = trueScript()  ;break;
            }
            retValue[i]=script;
        }
        return retValue;
    }


    public static void main(String args[]) {
        final int N = 200;
        String[] scripts = randomScripts(N);
        Boolean[] eval = LogicalProcessor.evaluate(preamble(), scripts);
        System.out.println("evaluation is: "+print(eval));
    }

    private static String print(Boolean[] eval) {
        return StringUtils.join(eval, ",");
    }
}