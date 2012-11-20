package gr.neuropublic.TestApp.backingBeans;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.TestApp.entities.*;
import gr.neuropublic.base.IdComparator;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "aController")
@SessionScoped
public class AController extends CRUDTableController<A> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(AController.class);

    @EJB
    private gr.neuropublic.TestApp.facades.IAFacade.ILocal ejbFacade;
    
    @EJB
    private gr.neuropublic.TestApp.facades.IBFacade.ILocal ejbFacadeB;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }
   
    protected IFacade getFacadeB() {
        return ejbFacadeB;
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
    
    /*****************************CUSTOM CODE*********************************/
    
    /**
     * Gets the detail Items of A
     * @return List<B>
     */
    public List<B> getBitemsOfA()
    {
        List<B> list = new ArrayList<B>();
        if (getCurrent()!=null)
        {
            list =new  ArrayList(getCurrent().getBaCollection());
        }
        return list;
    }
    
    private B currentB;
    public B getCurrentB()
    {
        return currentB;
    }
    
    public void setCurrentB(B currentB)
    {
        this.currentB = currentB;
    }
    

    /**
     * Create a new B using the current A
     */
    public void newB()
    {
        B b = (B) getFacadeB().initRow();
        b.setA(this.getCurrent());
        
//        //If a B is selected. Add the new B row below the selected. Else add it at the bottom
//        if (getCurrentB() != null) 
//        {
//            IdComparator idComparator = new IdComparator();
//            PersistentBag bitemsOfA = (PersistentBag) getCurrent().getBaCollection();           
//            Collections.sort(bitemsOfA, idComparator);            
//            int index = Collections.binarySearch(bitemsOfA, getCurrentB(), idComparator);
//            bitemsOfA.add(index + 1, b);
//        } 
//        else 
//        {
//            this.getCurrent().getBaCollection().add(b);
//        }
        
        this.getCurrent().getBaCollection().add(b);
        edit(this.getCurrent());
    }
    
    /**
     * Delete current B
     * @param entity 
     */
    public void deleteCurrentB(B entity)
    {
        this.getCurrent().getBaCollection().remove(entity);        
        edit(this.getCurrent());
    }
    
    
 

    

}
