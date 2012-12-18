import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;


import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.FileReader;


public class ParseTest {

    public static void main(String args[]) throws Exception {
        PythonInterpreter pi = new PythonInterpreter();
        //        pi.set("params", params);
        //        pi.set("sql", sql);
        //        pi.set("u", new JythonUtils(l));
        PyObject result = null;
        PyCode code = pi.compile(new BufferedReader(new FileReader("out-script")));
        // result = pi.eval(code);
    }

}