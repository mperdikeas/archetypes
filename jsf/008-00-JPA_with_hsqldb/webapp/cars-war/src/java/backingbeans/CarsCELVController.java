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

    private CELVControllerEnum state = CELVControllerEnum.LIST;
    public CELVControllerEnum getState() { 
        l.info("state queried; returning: "+state);
        return state;
    }

    private boolean loadDatabase = true;

    private Car newItem = new Car();
    public Car getNewItem ()            { return newItem; }
    public void setNewItem(Car newItem) { this.newItem = newItem; }


    private Car current;                                                                                                                                    
    public Car getCurrent() {return current;}                                                                                                               
    public void setCurrent(Car current) {                                                                                                                   
        l.info("setCurrent("+current+")");
        this.current = current;
    }    

    List<Car> removedItems  = new ArrayList<Car>();
    List<Car> createdItems  = new ArrayList<Car>();
    List<Car> modifiedItems = new ArrayList<Car>();
    List<Car> items;


    public void synchItemsFromDB() {
        items = carFacade.findAll();
        removedItems  = new ArrayList();
        createdItems  = new ArrayList();
        modifiedItems = new ArrayList();
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
        removedItems = new ArrayList();
        for (Car car : createdItems) {
            createInDB(car);
        }
        createdItems = new ArrayList();
        for (Car car : modifiedItems) {
            editInDB(car);
        }
        modifiedItems = new ArrayList();
    }

    public String add() {
        state = CELVControllerEnum.OPEN_FOR_CREATION;
        return null;
    }

    public void newItemDone() {
        items.add(newItem);
        createdItems.add(newItem);
        newItem = new Car();
        state = CELVControllerEnum.LIST;
    }

    public String newItemCancel() {
        state = CELVControllerEnum.LIST;
        return null;
    }

    private void createInDB(Car car) {
        try {
            carFacade.create(car);
            FacesContext.getCurrentInstance().addMessage("foo", new FacesMessage(FacesMessage.SEVERITY_INFO, "row added","row added"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("CAR-form:messagePanel", new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                                                   "row could not be created","row could not be created"));
        }
    }

    private void editInDB(Car car) {
        try {
            carFacade.edit(car);
            FacesContext.getCurrentInstance().addMessage("foo", new FacesMessage(FacesMessage.SEVERITY_INFO, "row edited","row edited"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("CAR-form:messagePanel", new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                                                   "row could not be edited","row could not be edited"));
        }
    }


    public void modify() {
        modifiedItems.add(current);
        state = CELVControllerEnum.OPEN_FOR_MOD;
    }

    public void modItemDone() {
        state = CELVControllerEnum.LIST;
    }


    public void modItemCancel() {
        state = CELVControllerEnum.LIST;
    }

}

