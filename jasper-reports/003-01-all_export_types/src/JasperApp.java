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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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


public class JasperApp
{
	
	public static void main(String[] args) throws JRException {
            switch (args[0]) {
                case "all": allExportFormats(args[1]);
                    break;
                case "pdf": pdf(args[1]); 
                    break;
                default:
                    throw new RuntimeException("unrecognized case");
            }
            System.err.println("exiting "+JasperApp.class.getName()+"#main");
	}

        private static String className         () { return JasperApp.class.getName() ; }
        private static String reportCoreName    () { return "FirstJasper"             ; }
        private static String compiledReportName() { return reportCoreName()+".jasper"; }


	private static Connection getHsqlConnection() throws JRException {
		Connection conn;
		try {
			String driver = "org.hsqldb.jdbcDriver";
			String connectString = "jdbc:hsqldb:hsql://localhost";
			String user = "sa";
			String password = "";
			Class.forName(driver);
			conn = DriverManager.getConnection(connectString, user, password);
		}
		catch (ClassNotFoundException e) {
			throw new JRException(e);
		}
		catch (SQLException e) {
			throw new JRException(e);
		}
		return conn;
	}

        private static Map parametersMap() throws JRException {
            Map parameters = null;
            {
                Image image = Toolkit.getDefaultToolkit().createImage(JRLoader.loadBytesFromResource("dukesign.jpg"));
                MediaTracker traker = new MediaTracker(new Panel());
                traker.addImage(image, 0);
                try {
                    traker.waitForID(0);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
                parameters = new HashMap();
                parameters.put("ReportTitle", "The First Jasper Report Ever");
                parameters.put("MaxOrderID", new Integer(10500));
                parameters.put("SummaryImage", image);
           }
           return parameters;
        }

        private static File fill() throws JRException {
		long start = System.currentTimeMillis();
                String sourceFileLocation = "reports/"+compiledReportName();
		System.err.println(" sourceFileLocation : " + sourceFileLocation);
		JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromLocation(sourceFileLocation);
		pixelPerfectJasperPrintObject = JasperFillManager.fillReport(jasperReport, parametersMap(), getHsqlConnection());
                File destTempFile = null;
                try {
                    destTempFile = File.createTempFile("jasper-"+className(), ".jrprint");
                } catch (IOException e) {
                    throw new ExceptionAdapter(e);
                }
		JRSaver.saveObject(pixelPerfectJasperPrintObject, destTempFile);
		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
                return destTempFile;
	}
        
        private static JasperPrint pixelPerfectJasperPrintObject = null;
        private static JasperPrint getJasperPrintObject() {return pixelPerfectJasperPrintObject;}

        private static File pixelPerfectJRPrintFile = null;
        private static File getJRPrintFile() throws JRException {
            if (pixelPerfectJRPrintFile == null) 
                pixelPerfectJRPrintFile = fill();
            return pixelPerfectJRPrintFile;
        }

       
        // the below method actually prints in a printer (I should test it at home - but is now broken cause the .jrprint
        // file is located in a temporary folder - to fix it I'll have to change its signature like the pdf() method
	public static void print() throws JRException {
		long start = System.currentTimeMillis();
		JasperPrintManager.printReport("build/reports/"+className()+"Report.jrprint", true);
		System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}

        public static void allExportFormats(String whereToProduceExport) throws JRException {
            pdf(whereToProduceExport);
            csv(whereToProduceExport);
            xml(whereToProduceExport);
            xmlEmbed(whereToProduceExport);
            html(whereToProduceExport);
            rtf(whereToProduceExport);
            xls(whereToProduceExport);
            csvMetadata(whereToProduceExport);
            odt(whereToProduceExport);
            ods(whereToProduceExport);
            docx(whereToProduceExport);
            xlsx(whereToProduceExport);
            pptx(whereToProduceExport);
            xhtml(whereToProduceExport);
        }
	
	private static void pdf(String whereToProduceExport) throws JRException {
                File jrprintFile = getJRPrintFile();
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToPdfFile(jrprintFile.getAbsolutePath(), whereToProduceExport+"/"+reportCoreName()+".pdf");
		System.err.println("PDF creation time is : " + (System.currentTimeMillis() - start));
	}

        private static File destFile(String whereToProduceExport, String extension) {
            return new File(whereToProduceExport, reportCoreName()+"."+extension);
        }

	private static void csv(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                File sourceFile = getJRPrintFile();
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
		//File destFile = new File(whereToProduceExport, reportCoreName()+".csv");
                File destFile = destFile(whereToProduceExport, "csv");
                System.err.println("csv destFile is: "+destFile.getAbsolutePath());
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		exporter.exportReport();
		System.err.println("CSV creation time : " + (System.currentTimeMillis() - start));
	}

	public static void xml(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToXmlFile(getJasperPrintObject(), destFile(whereToProduceExport, "xml").getAbsolutePath(), false);
		System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
	}

	public static void xmlEmbed(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToXmlFile(getJasperPrintObject(), destFile(whereToProduceExport, "emb-xml").getAbsolutePath(), true);
		System.err.println("XML creation time : " + (System.currentTimeMillis() - start));
	}
	
	
	public static void html(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToHtmlFile(getJasperPrintObject(), destFile(whereToProduceExport, "html").getAbsolutePath());
		System.err.println("HTML creation time : " + (System.currentTimeMillis() - start));
	}

	public static void rtf(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "rtf").toString());
		exporter.exportReport();
		System.err.println("RTF creation time : " + (System.currentTimeMillis() - start));
	}
	
	public static void xls(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
		Map dateFormats = new HashMap();
		dateFormats.put("EEE, MMM d, yyyy", "ddd, mmm d, yyyy");
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "xls").toString());
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.FORMAT_PATTERNS_MAP, dateFormats);
		exporter.exportReport();
		System.err.println("XLS creation time : " + (System.currentTimeMillis() - start));
	}

	public static void csvMetadata(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".metadata.csv");
		JRCsvMetadataExporter exporter = new JRCsvMetadataExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "metadata.csv").toString());
		exporter.exportReport();
		System.err.println("CSV creation time : " + (System.currentTimeMillis() - start));
	}

	public static void odt(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".odt");
		JROdtExporter exporter = new JROdtExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "odt").toString());
		exporter.exportReport();
		System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
	}
	
	public static void ods(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".ods");
		JROdsExporter exporter = new JROdsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "ods").toString());
		exporter.exportReport();
		System.err.println("ODS creation time : " + (System.currentTimeMillis() - start));
	}

	public static void docx(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".docx");
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "docx").toString());
		exporter.exportReport();
		System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
	}

	public static void xlsx(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xlsx");
		Map dateFormats = new HashMap();
		dateFormats.put("EEE, MMM d, yyyy", "ddd, mmm d, yyyy");
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "xlsx").toString());
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.FORMAT_PATTERNS_MAP, dateFormats);
		exporter.exportReport();
		System.err.println("XLSX creation time : " + (System.currentTimeMillis() - start));
	}
	

	public static void pptx(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".pptx");
		JRPptxExporter exporter = new JRPptxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "pptx").toString());
		exporter.exportReport();
		System.err.println("PPTX creation time : " + (System.currentTimeMillis() - start));
	}
	
	public static void xhtml(String whereToProduceExport) throws JRException {
		long start = System.currentTimeMillis();
                //		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".x.html");
		JRXhtmlExporter exporter = new JRXhtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrintObject());
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile(whereToProduceExport, "x.html").toString());
		exporter.exportReport();
		System.err.println("XHTML creation time : " + (System.currentTimeMillis() - start));
	}
	

}

