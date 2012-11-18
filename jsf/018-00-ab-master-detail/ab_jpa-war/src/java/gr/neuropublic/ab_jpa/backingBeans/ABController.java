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

@ManagedBean(name = "aBController")
@ViewScoped
public class ABController extends CRUDTableController<B> implements Serializable {

    private static Logger l = LoggerFactory.getLogger(ABController.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IBFacade.ILocal ejbFacade;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }

    @Override
    protected List filterData(Map filters, int[] range, int[] rowCount, String sortField, boolean sortOrder) {
        // l.info(String.format("BBBBBBBBBBBBBBBB view context is: %d", System.identityHashCode(FacesContext.getCurrentInstance().getViewRoot())));
        // l.info(String.format("BBBBBBBBBBBBBBBB filterData %d", System.identityHashCode(this)));
        //get parent controller
        EntityBasedController<A> parController = (EntityBasedController<A>)JsfUtil.accessBeanFromFacesContext("aController");
        // get parent enity (selected row in parent controller)
    	A parEntity = parController.getCurrent();
        //add filter
        if (parEntity!=null && parEntity.getId() != null)
    	    filters.put("a", parEntity.getId());
        else
            filters.put("a", -1);
        //call base method
        List ret = super.filterData(filters, range, rowCount, sortField, sortOrder);
        // try { Thread.sleep(10*1000); } catch (Exception e) {}
        return ret;
    }
    	
    @Override
    public void addRow() {
        //get parent controller
        EntityBasedController<A> parController = (EntityBasedController<A>)JsfUtil.accessBeanFromFacesContext("aController");
        // get parent enity (selected row in parent controller)
    	A parEntity = parController.getCurrent();

        B current = (B)getFacade().initRow();
    	current.setA(parEntity);
        getTransactionManager().create(current);
        recreateModel();
    }



	

    public void selectCurrent(ActionEvent actionEvent) {  
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
