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
import java.util.Set;
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

import facades.IAFacade;

import facades.IBFacade;

import mutil.base.BooleanToggler;
import entities.A;
import entities.B;
import base.IFacade;

@ManagedBean
@ViewScoped
public class ABLController implements Serializable {

    private static final Logger l = Logger.getLogger(ABLController.class.getName());

    @EJB(beanName = "AFacade")
    private IAFacade.ILocal aFacade;

    @EJB(beanName = "BFacade")
    private IBFacade.ILocal bFacade;

    @PostConstruct
    protected void initializeMasterRecord() {
        if (masterRecord == null) {
            masterRecord = (A) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("the-master-record");
        }
    }

    public String saveAndBackAtStart() {
        l.info("ABLController: about to persist");
        if (!aFacade.emContains(masterRecord)) {
            l.info("Entity is detached.");
            aFacade.merge(masterRecord);
        }
        return "alist";
    }

    public String saveAndStayHere() {
        l.info("ABLController: about to persist");
        if (!aFacade.emContains(masterRecord)) {
            l.info("Entity is detached.");
            masterRecord=aFacade.merge(masterRecord);
        }
        return null;
    }

    private A masterRecord;
    
    public A getMasterRecord() {
        l.info("getMasterRecord() called");
        return masterRecord;
    }

    public List<B> getDetailRecords() {
        List<B> retValue = new ArrayList<B>(masterRecord.getBCollection());
        l.info("ABLController#getDetailRecords() returning a list of: "+retValue.size()+" elements.");
        return retValue;
    }

    private B currentDetailRecord;
    public void setCurrentDetailRecord(B detailRecord) {
        l.info("*************************************************");
        l.info("*************************************************");
        l.info("*************************************************");
        l.info("setting current detail record to: "+detailRecord);
        this.currentDetailRecord = detailRecord;
    }

    public B getCurrentDetailRecord() {
        return currentDetailRecord;
    }

    public void addB() {
        B b = new B();
        b.setAId(masterRecord);
    }

    public void deleteCollection(B b) {
        l.info("deleting using collection change");
        b.setAId(null);
    }


    public void deleteJPA(B b) { // line-117
        l.info("JPA REMOVE STEP  1 # master record is: "+masterRecord);
        boolean necessaryToClipTheTree = true;
        if ( !necessaryToClipTheTree )
            b.preRemoveCutFromA();
        l.info("JPA REMOVE STEP  2 # master record is: "+masterRecord);
        bFacade.remove(masterRecord, b);
        l.info("JPA REMOVE STEP  3 # master record is: "+masterRecord);
        boolean shouldIRefresh = false;  // I shouldn't refresh cause if I do, the contents of the A graph will overwrite the removal change - but maybe this is fixed in this STAGE (12) - I don't have the time to check.
        if (shouldIRefresh) {
            l.info("masterRecord is detached? "+ (!aFacade.emContains(masterRecord)));
            masterRecord = aFacade.merge(masterRecord);
        }
        l.info("masterRecord is detached? "+ (!aFacade.emContains(masterRecord)));
        masterRecord = aFacade.find(masterRecord.getId());
        l.info("done deleting the entity with the JPA method, masterRecord now is: "+masterRecord);
    }
    private BooleanToggler bT = new BooleanToggler();
    public void deleteRandom() {
        B b = currentDetailRecord;
        if (b==null) throw new RuntimeException("you have to select a row first");
        if (bT.getValueAndToggle())
            deleteCollection(b);
        else
            deleteJPA(b);
    }

}

