package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;


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

import mutil.base.Pair;
import mutil.base.Triad;
import mutil.base.ExceptionAdapter;
import mutil.base.BooleanToggler;




@ManagedBean
@SessionScoped
public class UserLController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String CLASSNAME=UserLController.class.getName();


    public UserLController() {
        items = new ArrayList<String>();
    }


    private List<String> items;

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

    public void addItem(String item) {
        items.add(item);
    }



}
