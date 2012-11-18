package gr.neuropublic.ab_jpa.backingBeans;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.ab_jpa.entities.*;
import gr.neuropublic.jsf.base.CRUDTableController;
import gr.neuropublic.jsf.base.EntityBasedController;
import gr.neuropublic.jsf.base.LovHelper;
import gr.neuropublic.jsf.util.*;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "bController")
@SessionScoped
public class BController extends EntityBasedController<B> {

    private static Logger logger = LoggerFactory.getLogger(BController.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IBFacade.ILocal ejbFacade;

    protected gr.neuropublic.ab_jpa.facades.IBFacade.ILocal getFacade() {
        return ejbFacade;
    }

    public enum EditMode {
        CREATE, UPDATE
    }

    EditMode editMode;

    public BController() {
        this.editMode = EditMode.CREATE;
    }
    
    BSearchController searchController;

    public void initForEdit(BSearchController searchController, B itemToEdit) {
        this.searchController = searchController;
        this.editMode = EditMode.UPDATE;
        setCurrent(itemToEdit);
    }

    public Boolean getDeleteButtonIsDisable() { return editMode == EditMode.CREATE;}

    
    public void initForCreate(BSearchController searchController) {
        this.searchController = searchController;
        preNew();
    }

    public void preNew() {
        this.editMode = EditMode.CREATE;
        setCurrent(getFacade().initRow());
    }



    public void commit(ActionEvent actionEvent) {  
    }  	

    public void cancelListener(ActionEvent actionEvent) {
        preNew();
    }

    public void searchListener(ActionEvent actionEvent) {
    }

    public void deleteListenerListener(ActionEvent actionEvent) {
        preNew();
    }


    public Integer getId() {
        B cur = getCurrent();
        if (cur != null)
            return cur.getId();
        return null;
    }

    public void setId(Integer val) {
        B cur = getCurrent();
        if (cur != null) {
            if (getId() == null || !getId().equals(val)) {
                cur.setId(val);
            }
        }
    }

    public String getB1() {
        B cur = getCurrent();
        if (cur != null)
            return cur.getB1();
        return null;
    }

    public void setB1(String val) {
        B cur = getCurrent();
        if (cur != null) {
            if (getB1() == null || !getB1().equals(val)) {
                cur.setB1(val);
            }
        }
    }

    public A getA() {
        B cur = getCurrent();
        if (cur != null)
            return cur.getA();
        return null;
    }

    public void setA(A val) {
        B cur = getCurrent();
        if (cur != null) {
            if (getA() == null || !getA().equals(val)) {
                cur.setA(val);
            }
        }
    }

    // LOV listeners
    public void prepareA() {
        ALovBean lovController = (ALovBean) JsfUtil.accessBeanFromFacesContext("aLovBean");
        lovController.setA1_rendered(true);
        lovController.setLovHelper(
                    new LovHelper<A>() {
                        public void saveEnity(A ent) {
                            setA(ent);
                        }
                        public Map getFilters() {
                            HashMap<String, Object> ret = new HashMap<String, Object>();
                            return ret;
                        }

                    }
                );
        
    }


}