import java.io.StringWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import mutil.base.FileUtil;

public class ImportTest {
    public static void main(String[] args) throws Exception {
        ImportTest it = new ImportTest();
        it.work();
    }
    public void work() throws IOException {
            PythonInterpreter pi = new PythonInterpreter();
            PyObject result = null;
            String script = "import datetime"; // "import re" exemplifies the exact same behaviour
            
            //String preample = FileUtil.getASCIIResourceAsString("datetime.py");
            StringWriter writer = new StringWriter();
            IOUtils.copy(getClass().getResourceAsStream("datetime.py"), writer, "US-ASCII");
            String preample = writer.toString();
            PyCode code = pi.compile(preample+"\n"+script); 
            result = pi.eval(code);
    }	


}