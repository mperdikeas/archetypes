package gr.neuropublic.ab_jpa.backingBeans;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.ab_jpa.entities.*;
import gr.neuropublic.jsf.base.CRUDTableController;
import gr.neuropublic.jsf.base.EntityBasedController;
import gr.neuropublic.jsf.base.LazyDataModel;
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
import org.primefaces.model.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@ManagedBean(name = "aBController")
@SessionScoped
public class ABController extends CRUDTableController<B> implements Serializable {

    private static Logger l = LoggerFactory.getLogger(ABController.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IBFacade.ILocal ejbFacade;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }

    @PostConstruct
    public void foo() {
        l.info(String.format("post construct called on BBBBBBBBBBBBBBBB managed bean %d", System.identityHashCode(this)));
    }


    @Override
    protected List filterData(Map filters, int[] range, int[] rowCount, String sortField, boolean sortOrder) {
    
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
        return super.filterData(filters, range, rowCount, sortField, sortOrder);
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
    
        /*****************************CUSTOM CODE*********************************/
    

    @Override
    public LazyDataModel getItems()
    {
        l.debug("getItems() " + getFacade().getClass());
        if (items == null) {
            items = createPageDataModel();
        }
        return items;
    }


    @Override
    public LazyDataModel createPageDataModel()
    {

        LazyDataModel model = new LazyDataModel()
        {
            @Override
            public List load(int first, int pageSize, String sortField,
                    SortOrder sortOrder, Map filters)
            {
                EntityBasedController<A> parController = (EntityBasedController<A>)JsfUtil.accessBeanFromFacesContext("aController");
                A parEntity = parController.getCurrent();
                parEntity.getBaCollection().size();
                List<B> finalData = new ArrayList(parEntity.getBaCollection());
                return finalData;
            }
        };

        return model;
    }


}
