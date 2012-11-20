package gr.neuropublic.jsf.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import org.slf4j.LoggerFactory;

/**
 *
 * @author g_dimitriadis
 */
public class ReportsService {

    private final static String DATASOURCE_CONTEXT = "java:/cashflow";
    
    private static org.slf4j.Logger logger =
            LoggerFactory.getLogger(ReportsService.class);

    private static Connection getConnection() throws JRException {
        Connection conn = null;
        Context initialContext;
        try {
            initialContext = new InitialContext();
        } catch (NamingException ex) {
            throw new JRException(ex);
        }

        try {
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            if (datasource != null) {
                conn = datasource.getConnection();
            }
        } catch (NamingException ex) {
            throw new JRException(ex);
        } catch (SQLException e) {
            throw new JRException(e);
        }
        return conn;
    }

    private static void putPrintObjectInSession(Map parameters, String reportName) throws JRException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        logger.info("report name = "+reportName);
        String reportFileName = context.getRealPath("/reports/" + reportName + ".jasper");
        File reportFile = new File(reportFileName);
        
        if (!reportFile.exists()) {
            throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first and bundled in the war.");
        }
        Connection connection = getConnection();
        JasperPrint jasperPrint = null;
        try {
            jasperPrint =
                    JasperFillManager.fillReport(
                    reportFileName,
                    parameters,
                    getConnection());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        ((HttpSession) externalContext.getSession(false)).setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
    }

    public static void exportReport(Map parameters, String reportName) throws IOException, JRException {
        putPrintObjectInSession(parameters, reportName);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath()+"/servlets/pdf");
        
        FacesContext.getCurrentInstance().responseComplete();
    }
}