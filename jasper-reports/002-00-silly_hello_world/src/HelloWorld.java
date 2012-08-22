import java.awt.Color;
import java.io.File;

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


public class HelloWorld
{
	
	public static void main(String[] args) throws JRException {
            switch (args[0]) {
            case "fill": fill();
                break; 
            case "pdf": pdf(); 
                break;
            default:
                throw new RuntimeException("unrecognized case");
            }
	}

	public static void fill() throws JRException {
		long start = System.currentTimeMillis();
		File sourceFile = new File("build/reports/"+HelloWorld.class.getName()+"Report.jasper");
		System.err.println(" : " + sourceFile.getAbsolutePath());
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, (JRDataSource)null);
		
		File destFile = new File(sourceFile.getParent(), jasperReport.getName() + ".jrprint");
		JRSaver.saveObject(jasperPrint, destFile);
		
		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
	}

       
        // the below method actually prints at a printer (I should test it at home).
	public static void print() throws JRException {
		long start = System.currentTimeMillis();
		JasperPrintManager.printReport("build/reports/"+HelloWorld.class.getName()+"Report.jrprint", true);
		System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}

	
	public static void pdf() throws JRException {
		long start = System.currentTimeMillis();
                // the below method as a convenience takes only one parameter and produces the PDF file next to it
		JasperExportManager.exportReportToPdfFile("build/reports/"+HelloWorld.class.getName()+"Report.jrprint");
		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}
}
            