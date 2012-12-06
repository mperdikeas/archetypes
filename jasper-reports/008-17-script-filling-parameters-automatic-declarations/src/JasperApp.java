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
import java.util.SortedSet;
import java.util.TreeSet;
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

import org.apache.commons.io.FileUtils;

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
            System.out.println(JasperApp.class.getName()+"::main("+StringUtils.join(args, ','));
            switch (args[2]) {
                case "pdf":
                    pdf(args[1]); 
                    break;
                case "print":
                    prepareParameters(args[0], args[1]);
                    break;
                case "test-prob-queries":
                    runQuery(args[0]);
                    break;
                default:
                    throw new RuntimeException(String.format("unrecognized case: %s\nWas expecting one of: 'print', 'test-prob-queries'", args[0]));
            }
	}

        private static String className         () { return JasperApp.class.getName() ; }
        private static String reportCoreName    () { return "groups"             ; }
        private static String compiledReportName() { return reportCoreName()+".jasper"; }
        private static String pdfReportName     () { return reportCoreName()+".pdf"   ; }


    private static Properties readProperties(File paramsFile) throws IOException {
        Properties prop = new Properties();
        InputStream stream = new FileInputStream(paramsFile);
        prop.load(stream);
        stream.close();
        return prop;
    }

    private static void runQuery(String paramsFile) throws SQLException, ClassNotFoundException, IOException {
        QueryJythonHolder sql = new QueryJythonHolder(getConnection(new File(paramsFile)));
        sql.testProblemQueries();
    }

    private static Connection getConnection(File paramsFile) throws ClassNotFoundException, SQLException, IOException {
        Properties props = readProperties(paramsFile);
	Connection conn;
	String driver        = props.getProperty("driver");
	String connectString = props.getProperty("url");   
	String user          = props.getProperty("user");  
	String password      = props.getProperty("pwd");   
	Class.forName(driver);
	conn = DriverManager.getConnection(connectString, user, password);
        System.out.println((conn!=null)?"Connection obtained":"CONNECTION NOT OBTAINED");
	return conn;
    }

    private static Map<String, Object> prepareParameters(String paramsFile, String scriptFile) throws ClassNotFoundException, SQLException, IOException, PyException {
        String script         = FileUtil.readUTF8FileAsSingleString(new File(scriptFile), "\n");
        String scriptUTFReady = escapeUTFForPythonScript(script);
        String scriptUTFReadyWtFuncs = StringUtils.join(PYTHON_HEADER, "\n")+"\n"+scriptUTFReady+"\n"+String.format(StringUtils.join(PYTHON_FOOTER, "\n"), "params");
        FileUtils.writeStringToFile(new File("out-script"), scriptUTFReadyWtFuncs);
        System.out.println(scriptUTFReadyWtFuncs+"\n---------------------------");
        QueryJythonHolder sql = new QueryJythonHolder(getConnection(new File(paramsFile)));
        Map<String, Object> parameters = null;
        try {
            parameters = processParameters(sql, scriptUTFReadyWtFuncs);
        } finally {
            FileUtils.writeStringToFile(new File("out-queries"), sql.getQueries());
        }
        FileUtils.writeStringToFile(new File("out-queries"), sql.getQueries());
        System.out.println("---------------------------");
        unescapeStringParameters(parameters);
        printParameters(parameters);
        writeParameters(parameters, new File("out-results"));
        return parameters;
    }

    private static File fill() throws ClassNotFoundException, SQLException, IOException, JRException {
            long start = System.currentTimeMillis();
            String sourceFileLocation = "reports/"+compiledReportName();
            System.err.println(" sourceFileLocation : " + sourceFileLocation);
            JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromLocation(sourceFileLocation);
            Map<String, Object> parameters = prepareParameters("this will break later", "this will break later");
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


    private static final String PYTHON_HEADER[] = {
        //        "# -*- coding: utf-8 -*-",
        "import re",
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
        "    def len(self):",
        "        return self._results.size()",
        "",
        "def sqlm(query, num, tolerance):",
        "    return MySQLrecords(sql.qm(query, num, tolerance))",
        "",
        "def sqls(query):",
        "    return MyRecord(sql.q(query))"
        };


    private static final String PYTHON_FOOTER[] = {
        "REPORT_PARAMS = re.compile(\"(STR|BD|I|D)_\\w*\")",
        "",
        "def isReportGlobal(s):",
        "    return REPORT_PARAMS.match(s)",
        "",
        "for (k,v) in globals().items():",
        "    if isReportGlobal(k):",
        "        %s.put(k, v)"
        };


    private static String escapeUTFForPythonScript(String script) {
        String escapedScript                  = StringEscapeUtils.escapeJava(script);
        String escapedScriptNewLinesCorrected = escapedScript.replaceAll("\\\\n", "\n");
        String escapedScriptQuotesCorrected   = escapedScriptNewLinesCorrected.replaceAll("\\\\'", "\'");
               escapedScriptQuotesCorrected   = escapedScriptQuotesCorrected.replaceAll("\\\\\"", "\"");
        return escapedScriptQuotesCorrected;
    }

    private static void unescapeStringParameters(Map<String, Object> params) {
        for (String key : params.keySet())
            if (key.startsWith("STR_"))
                params.put(key, StringEscapeUtils.unescapeJava((String)params.get(key)));
    }

    private static void printParameters(Map<String, Object> params) {
        SortedSet<String> keys = new TreeSet<String>(params.keySet());
        for (String key : keys) {
            System.out.println(key+"  -->  "+String.valueOf(params.get(key)));
        }
    }

    private static void writeParameters(Map<String, Object> params, File out) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (String key : params.keySet()) {
            sb.append(key+"  -->  "+String.valueOf(params.get(key))+"\n");
        }
        FileUtils.writeStringToFile(out, sb.toString());
    }

    public static Map<String, Object> processParameters(QueryJythonHolder sql, String script) throws PyException {
        Map<String, Object> params = new HashMap<String, Object>();
        {
            PythonInterpreter pi = new PythonInterpreter();
            pi.set("params", params);
            pi.set("sql", sql);
            pi.set("u", new JythonUtils());
            PyObject result = null;

            PyCode code = pi.compile(script);
            result = pi.eval(code);
            l.info("evalution result = "+result);
        }
        return params;
    }

    public static void pdf(String whereToProducePDF) throws JRException, IOException, SQLException, PyException, ClassNotFoundException {
       File jrprintFile = fill();
       long start = System.currentTimeMillis();
       JasperExportManager.exportReportToPdfFile(jrprintFile.getAbsolutePath(), whereToProducePDF+"/"+pdfReportName());
       System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }
}

