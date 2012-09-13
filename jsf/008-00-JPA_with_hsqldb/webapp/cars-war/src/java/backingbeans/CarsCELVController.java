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
import javax.faces.application.FacesMessage;                                                                                                                

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

import facades.ICarFacade;
import entities.Car;

import mutil.base.ListUtil;

@ManagedBean
@ViewScoped
public class CarsCELVController implements Serializable {

    private static final String CLASSNAME=CarsCELVController.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    @EJB(beanName = "CarFacade")
    private ICarFacade.ILocal carFacade;

    private boolean loadDatabase = true;

    private Car current;                                                                                                                                    
    public Car getCurrent() {return current;}                                                                                                               
    public void setCurrent(Car current) {                                                                                                                   
        l.info("setCurrent("+current+")");
        this.current = current;
    }    

    List<Car> removedItems = new ArrayList<Car>();
    List<Car> items;
    List<Car> backupItems;

    public void synchItemsFromDB() {
        items = carFacade.findAll();
        backupItems = new ArrayList();
        removedItems = new ArrayList();
        for (Car car : items)
            backupItems.add(new Car(car));
    }

    public List<Car> getItems() { 
        if (loadDatabase) {
            synchItemsFromDB();
            loadDatabase = false;
        }
        return items;
    }

    public void remove() {
        ListUtil.remove(items, current);
        removedItems.add(current);
    }

    public void restoreFromDB() {
        synchItemsFromDB();
        backupItems = null;
    }
    
    private void removeFromDB(Car car) {
        try {
            carFacade.remove(car);     
            FacesContext.getCurrentInstance().addMessage("foo", new FacesMessage(FacesMessage.SEVERITY_INFO, "row deleted","row deleted"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("CAR-form:messagePanel", new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                                                   "row could not be deleted","row could not be deleted"));
        }  
    }

    public void commitToDB() {
        for (Car car : removedItems) {
            removeFromDB(car); 
        }
    }

}

