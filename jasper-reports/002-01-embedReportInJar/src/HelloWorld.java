import java.awt.Color;
import java.io.File;
import java.io.IOException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

import mutil.base.ExceptionAdapter;

public class HelloWorld
{
	
	public static void main(String[] args) throws JRException {
            switch (args[0]) {
                case "pdf": pdf(args[1]); 
                    break;
                default:
                    throw new RuntimeException("unrecognized case");
            }
	}

        private static String className         () { return HelloWorld.class.getName(); }
        private static String reportCoreName    () { return className()+"Report"      ; }
        private static String compiledReportName() { return reportCoreName()+".jasper"; }
        private static String pdfReportName     () { return reportCoreName()+".pdf"   ; }


        private static File fill() throws JRException {
		long start = System.currentTimeMillis();
                String sourceFileLocation = "reports/"+compiledReportName();
		System.err.println(" sourceFileLocation : " + sourceFileLocation);
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromLocation(sourceFileLocation);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, (JRDataSource)null);
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

       
        // the below method actually prints in a printer (I should test it at home - but is now broken cause the .jrprint
        // file is located in a temporary folder - to fix it I'll have to change its signature like the pdf() method
	public static void print() throws JRException {
		long start = System.currentTimeMillis();
		JasperPrintManager.printReport("build/reports/"+HelloWorld.class.getName()+"Report.jrprint", true);
		System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}

	
	public static void pdf(String whereToProducePDF) throws JRException {
                File jrprintFile = fill();
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToPdfFile(jrprintFile.getAbsolutePath(), whereToProducePDF+"/"+pdfReportName());
		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}
}
            