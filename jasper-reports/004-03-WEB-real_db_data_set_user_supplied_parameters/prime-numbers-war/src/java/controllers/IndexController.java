package controllers;


import java.io.Serializable;
import java.io.IOException;
import java.io.File;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedProperty;
import java.util.logging.Logger;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import java.util.Map;
import java.util.HashMap;
import javax.faces.event.ComponentSystemEvent;
import java.util.Collection;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;


@ManagedBean
@RequestScoped
public class IndexController implements Serializable {
    private final Logger l = Logger.getLogger( getClassName() );
    private String getClassName() { return this.getClass().getName(); }

    public IndexController() {
    }

    private static Connection getHsqlConnection() throws JRException {
        Connection conn;
        try {
                String driver        = "org.hsqldb.jdbcDriver";
                String connectString = "jdbc:hsqldb:hsql://localhost";
                String user          = "sa";
                String password      = "";
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

    private void putPrintObjectInSession() throws JRException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String reportFileName = context.getRealPath("/reports/PrimeNumbersReport.jasper");
        File reportFile = new File(reportFileName);
        if (!reportFile.exists())
            throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first and bundled in the war.");
        Map parameters = new HashMap();
        parameters.put("ReportTitle", "2nd Prime Numbers report");
        parameters.put("BaseDir", reportFile.getParentFile());
        JasperPrint jasperPrint = 
                JasperFillManager.fillReport(
                          reportFileName, 
                          parameters, 
                          getHsqlConnection()
                        );
        ((HttpSession) externalContext.getSession(false)).setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
    }

    public String exportPrimes() throws IOException, JRException {
        putPrintObjectInSession();
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();  
        externalContext.redirect("servlets/pdf");
        return null;
    }
}
