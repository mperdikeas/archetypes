import org.python.core.*;
import org.python.util.PythonInterpreter;


public class ImportTest {
    public static void main(String[] args) throws Exception {
            PythonInterpreter pi = new PythonInterpreter();
            PyObject result = null;

            PyCode code = pi.compile("import re");
            result = pi.eval(code);
    }	


}