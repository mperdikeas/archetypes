package gr.neuropublic.TestApp.backingBeans;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.TestApp.entities.*;
import gr.neuropublic.jsf.base.CRUDTableController;
import gr.neuropublic.jsf.base.EntityBasedController;
import gr.neuropublic.jsf.util.*;
import gr.neuropublic.jsf.base.LovHelper;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  
import gr.neuropublic.jsf.util.TriStateCheckboxToBooleanConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "bSearchController")
@SessionScoped
public class BSearchController  implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(BSearchController.class);

    @EJB
    private gr.neuropublic.TestApp.facades.IBFacade.ILocal ejbFacade;

    protected gr.neuropublic.TestApp.facades.IBFacade.ILocal getFacade() {
        return ejbFacade;
    }

    @ManagedProperty(value="#{bController}")
    private BController editController;
    public BController getEditController() {
        return editController;
    }
    public void setEditController(BController editController) {
        this.editController = editController;
    }


    public BSearchController() {
        
    }

    //b1
    private String b1 = null;
    public String getB1() {return this.b1;}
    public void setB1(String b1) {this.b1 = b1;}

    //a
    private A a = null;
    public A getA() {return this.a;}
    public void setA(A a) {this.a = a;}


    public List<B> getItemsFromDB() {
        return getFacade().findB(b1, a, 100);
    }

    private List<B> items = null;
    public List<B> getItems() {
        if (items == null) {
            items = getItemsFromDB();
        }
        return items;
    }

    private List<B> filteredItems;
    public List<B> getFilteredItems() {
        return filteredItems;
    }
    public void setFilteredItems(List<B> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public void requestDataFromDBListener(ActionEvent notUsed) {
        logger.info("@@@@ getItemsFromDBListener() called");
        items = null;
        items = getItems();
    }

    protected B current;
    
    public B getCurrent() {
        return current;
    }
    
    public void setCurrent(B current) {
        this.current = current;
    }


    public String prepareEdit() {
        B valToEdit = getCurrent();
        if (valToEdit != null) {
            getEditController().initForEdit(this, valToEdit);
            return "B";
        } else 
            return null;
    }

    public String prepareCreate() {
        getEditController().initForCreate(this);
        return "B";
    }

    public String delete() {
        return null;
    }


    // Lov action listeners
    public void prepareA() {
        ALovBean lovController = (ALovBean) JsfUtil.accessBeanFromFacesContext("aLovBean");
        lovController.setA1_rendered(true);
        lovController.setLovHelper(
                    new LovHelper<A>() {
                        public void saveEnity(A ent) {
                            setA(ent);
                        }
                    }
                );
        
    }

}