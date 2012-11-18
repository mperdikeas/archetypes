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
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@ManagedBean(name = "aController")
@ViewScoped
public class AController extends CRUDTableController<A> implements Serializable {

    private static Logger l = LoggerFactory.getLogger(AController.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IAFacade.ILocal ejbFacade;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }

    @PostConstruct
    public void foo() {
        l.info(String.format("post construct called on AAAAAAAAAAAAAAAA managed bean %d", System.identityHashCode(this)));
    }

    protected List filterData(Map filters, int[] range, int[] rowCount, String sortField, boolean sortOrder) {
        l.info(String.format("AAAAAAAAAAAAAAAA Managed bean is %d, view context is: %d", System.identityHashCode(this), System.identityHashCode(FacesContext.getCurrentInstance().getViewRoot())));
        return super.filterData(filters, range, rowCount, sortField, sortOrder);    
    }

	

    public void selectCurrent(ActionEvent actionEvent) {  
    }  	

    public Integer getId() {
        A cur = getCurrent();
        if (cur != null)
            return cur.getId();
        return null;
    }

    public void setId(Integer val) {
        A cur = getCurrent();
        if (cur != null) {
            if (getId() == null || !getId().equals(val)) {
                cur.setId(val);
            }
        }
    }

    public String getA1() {
        A cur = getCurrent();
        if (cur != null)
            return cur.getA1();
        return null;
    }

    public void setA1(String val) {
        A cur = getCurrent();
        if (cur != null) {
            if (getA1() == null || !getA1().equals(val)) {
                cur.setA1(val);
            }
        }
    }

    // LOV listeners

}
