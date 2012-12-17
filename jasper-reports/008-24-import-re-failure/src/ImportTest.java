import org.python.core.*;
import org.python.util.PythonInterpreter;


public class ImportTest {
    public static void main(String[] args) throws Exception {
            PythonInterpreter pi = new PythonInterpreter();
            PyObject result = null;

            PyCode code = pi.compile("import datetime"); // "import re" exemplifies the exact same behaviour
            result = pi.eval(code);
    }	


}