import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;

import org.apache.commons.lang3.StringUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiMetadataExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

import mutil.base.ExceptionAdapter;
import mutil.base.Holder;
import mutil.base.FileUtil;

import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringEscapeUtils;

public class JasperApp
{
    private static final Logger l = LoggerFactory.getLogger(JasperApp.class);
	
        public static void main(String[] args) throws Exception {
            switch (args[0]) {
                case "pdf":
                    pdf(args[1]); 
                    break;
                case "print":
                    prepareParameters(args[1]);
                    break;
                default:
                    throw new RuntimeException("unrecognized case");
            }
            System.err.println("exiting "+JasperApp.class.getName()+"#main");
	}

        private static String className         () { return JasperApp.class.getName() ; }
        private static String reportCoreName    () { return "groups"             ; }
        private static String compiledReportName() { return reportCoreName()+".jasper"; }
        private static String pdfReportName     () { return reportCoreName()+".pdf"   ; }


    private static Properties readProperties(File paramsFile) throws IOException {
        Properties prop = new Properties();
        // ClassLoader loader = Thread.currentThread().getContextClassLoader();           
        InputStream stream = new FileInputStream(paramsFile); // loader.getResourceAsStream("params.ini");
        prop.load(stream);
        stream.close();
        return prop;
    }

    private static Connection getConnection(File paramsFile) throws ClassNotFoundException, SQLException, IOException {
        Properties props = readProperties(paramsFile);
	Connection conn;
	String driver        = props.getProperty("driver"); // "org.postgresql.Driver";
	String connectString = props.getProperty("url");    // "jdbc:postgresql://localhost:5432/usermgmnt";
	String user          = props.getProperty("user");   // "gaia-user";
	String password      = props.getProperty("pwd");    // "gaia-user-pwd"
	Class.forName(driver);
	conn = DriverManager.getConnection(connectString, user, password);
        System.out.println("Connection obtained: "+((conn==null)?"null":"not null"));
	return conn;
    }

    private static Map<String, Object> prepareParameters(String paramsFile) throws ClassNotFoundException, SQLException, IOException {
        String script         = FileUtil.readFileAsSingleString(new File("script.py"), "\n");
        String scriptUTFReady = escapeUTFForPythonScript(script);
        String scriptUTFReadyWtFuncs = StringUtils.join(PYTHON_FUNCS, "\n")+"\n"+scriptUTFReady;
        System.out.println("escaped script is:\n"+scriptUTFReady+"\n---------------------------");
        Map<String, Object> parameters = processParameters(getConnection(new File(paramsFile)), scriptUTFReadyWtFuncs);
        unescapeStringParameters(parameters);
        printParameters(paramNames, parameters);
        return parameters;
    }

    private static File fill() throws ClassNotFoundException, SQLException, IOException, JRException {
            long start = System.currentTimeMillis();
            String sourceFileLocation = "reports/"+compiledReportName();
            System.err.println(" sourceFileLocation : " + sourceFileLocation);
            JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromLocation(sourceFileLocation);
            Map<String, Object> parameters = prepareParameters("this will break later");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, getConnection(new File("this will break later")));
            File destTempFile = null;
            try {
                destTempFile = File.createTempFile("jasper-"+className(), ".jrprint");
            } catch (IOException e) {
                throw new ExceptionAdapter(e);
            }
            JRSaver.saveObject(jasperPrint, destTempFile);
            System.err.println("Filling time : " + (System.currentTimeMillis() - start));
            return destTempFile;
    }

    private static final String PYTHON_FUNCS[] = {
        "class MyRecord:",
        "    def __init__(self, dictData):",
        "        self._dictData = dictData",
        "    def __getattr__(self, nameOfField):",
        //        "        global STR_debug",
        //        "        STR_debug = nameOfField",
        "        return self._dictData.f(nameOfField)",
        "    def __getitem__(self, idx):",
        "        return self._dictData[idx]",
        "",
        "",
        "class MySQLrecords:",
        "    def __init__(self, sqlData):",
        "        self._results = sqlData",
        "    def __getitem__(self, idx):",
        "        return MyRecord(self._results[idx])",
        "",
        "def sqlm(query, num, tolerance):",
        "    return MySQLrecords(sql.qm(query, num, tolerance))",
        "",
        "def sqls(query):",
        "    return MyRecord(sql.q(query))"};


    private static String escapeUTFForPythonScript(String script) {
        String escapedScript                  = StringEscapeUtils.escapeJava(script);
        String escapedScriptNewLinesCorrected = escapedScript.replaceAll("\\\\n", "\n");
        String escapedScriptQuotesCorrected   = escapedScriptNewLinesCorrected.replaceAll("\\\\'", "\'");
        //        String escapedScriptDoubleQuotesCorrected   = escapedScriptQuotesCorrected.replaceAll("\\\"", "\\\\\"");
        return escapedScriptQuotesCorrected;
    }

    private static void unescapeStringParameters(Map<String, Object> params) {
        for (String key : params.keySet())
            if (key.startsWith("STR_"))
                params.put(key, StringEscapeUtils.unescapeJava((String)params.get(key)));
    }

    private static void printParameters(List<String> paramNames, Map<String, Object> params) {
        for (String key : paramNames) {
            System.out.println(key+"  -->  "+String.valueOf(params.get(key)));
        }
    }

    public static Map<String, Object> processParameters(Connection conn, String script) throws PyException {
        Map<String, Object> params = new HashMap<String, Object>();
        {
            PythonInterpreter pi = new PythonInterpreter();
            QueryJythonHolder sql = new QueryJythonHolder(conn);
            pi.set("params", params);
            pi.set("sql", sql);
            pi.set("u", new JythonUtils());
            PyObject result = null;

            PyCode code = pi.compile(adorn(script, "params"));
            result = pi.eval(code);
            l.info("evalution result = "+result);
        }
        return params;
    }

    private static List<String> paramNames(String script, String paramRegExp) {
        List<String> retValue = new ArrayList<String>();
        Pattern p0 = Pattern.compile(paramRegExp);
        Matcher m = p0.matcher(script);
        while (m.find()) {
            if (!retValue.contains(m.group(0))) retValue.add(m.group(0));
        }
        return retValue;
    }

    public static void mmain(String args[]) {
        System.out.println(paramNames("STR_1=sdf I_3=I_2+I_3;BD_234\nBD_3; dfasdfsdfasdfasdfasdf", "(STR|BD|I|D)_\\w*"));
    }

    private static List<String> paramNames;
    private static final String adorn(String script, String paramsVar) {
       paramNames = paramNames(script, "(STR|BD|I|D)_\\w*");
       l.info(String.format("%d parameters detected", paramNames.size()));
       StringBuilder sb = new StringBuilder();
       sb.append("\n");
       for (String paramName : paramNames)
           sb.append(String.format("%s.put('%s', %s)\n", paramsVar, paramName, paramName));
       return script+sb.toString();
    }

       
    public static void pdf(String whereToProducePDF) throws JRException, IOException, SQLException, PyException, ClassNotFoundException {
       File jrprintFile = fill();
       long start = System.currentTimeMillis();
       JasperExportManager.exportReportToPdfFile(jrprintFile.getAbsolutePath(), whereToProducePDF+"/"+pdfReportName());
       System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }
}

