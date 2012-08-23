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


    private void putPrintObjectInSession() throws JRException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String reportFileName = context.getRealPath("/reports/PrimeNumbersReport.jasper");
        File reportFile = new File(reportFileName);
        if (!reportFile.exists())
            throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first and bundled in the war.");
        Map parameters = new HashMap();
        parameters.put("ReportTitle", "This is the 2nd Prime Numbers Report");
        parameters.put("BaseDir", reportFile.getParentFile());
        JasperPrint jasperPrint = 
                JasperFillManager.fillReport(
                          reportFileName, 
                          parameters, 
                          (JRDataSource) null // new WebappDataSource()
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
