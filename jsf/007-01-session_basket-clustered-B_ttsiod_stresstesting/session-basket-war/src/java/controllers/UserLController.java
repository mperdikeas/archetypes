package controllers;


import mutil.base.Util;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
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

import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;

import mutil.base.Pair;
import mutil.base.Triad;
import mutil.base.ExceptionAdapter;
import mutil.base.BooleanToggler;

@ManagedBean
@SessionScoped
public class UserLController implements Serializable {

    private static final String CLASSNAME=UserLController.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);


    private UserLControllerEnum state;

    public UserLControllerEnum getState() {
        return state;
    }

    public UserLController() {
        items = new ArrayList<String>();
        state = UserLControllerEnum.LIST;
    }


    private List<String> items;

    private String newItem;
    
    public String getNewItem() {
        return newItem;
    }
    
    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    private String current;
    
    public String getCurrent() {
        return current;
    }
    
    public void setCurrent(String current) {
        this.current = current;
    }

    public List<String> getItems() {
        return items;
    }

    private void addItem(String item) {
        items.add(item);
    }

    public String create() {
        state = UserLControllerEnum.OPEN_FOR_CREATION;
        return null;
    }

    public String save() {
        addItem(getNewItem());
        state = UserLControllerEnum.OPEN_FOR_CREATION;
        return null;
    }

  
    public void saveGrowl(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();  
        context.addMessage(null, new FacesMessage("Successfully added: '"+getNewItem()+"'"));
    }

    public String done() {
        setNewItem(""); // if I set this to null I get a "newItem:j_idt11: Validation Error: Value is required.  newItem:j_idt11: Validation Error: Value is required." exception
        state = UserLControllerEnum.LIST;
        return null;
    }
}
