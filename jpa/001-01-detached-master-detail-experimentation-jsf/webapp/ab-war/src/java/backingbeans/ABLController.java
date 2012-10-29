package backingbeans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
import java.util.List;
import javax.servlet.http.HttpSession;

import facades.IAFacadeLocal;
import facades.IAFacade;
import entities.A;
import entities.B;
import base.IFacade;

@ManagedBean
@ViewScoped
public class ABLController implements Serializable {

    private static final Logger l = Logger.getLogger(ABLController.class.getName());

    @EJB(beanName = "AFacade")
    private IAFacadeLocal aFacade;

    @PostConstruct
    protected void initializeMasterRecord() {
        if (masterRecord == null) {
            masterRecord = (A) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("the-master-record");
        }
    }


    public String gotoCreate() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("create-mode", true);
        return "aedit";
    }

    private A masterRecord;
    
    public A getMasterRecord() {
        l.info("getMasterRecord() called");
        return masterRecord;
    }

    private B currentDetailRecord;
    public void setCurrentDetailRecord(B detailRecord) {
        this.currentDetailRecord = currentDetailRecord;
    }

    public B getCurrentDetailRecord() {
        return currentDetailRecord;
    }

    public void addB() {
        B b = new B();
        b.setAId(masterRecord);
    }

}

