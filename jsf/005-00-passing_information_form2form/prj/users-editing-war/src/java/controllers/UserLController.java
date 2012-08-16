package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

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

import entities.*;
import facades.*;


@ManagedBean
@RequestScoped
public class UserLController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String CLASSNAME=UserLController.class.getName();

    @ManagedProperty(value="#{userEController}")    
    private UserEController userEController;
    public void setUserEController(UserEController userEController) {
        this.userEController = userEController;
    }

    @EJB
    private UserFacade userFacade;
    public UserFacade getFacade() {
        return userFacade;
    }

    private User current;
    private List<User> items;
    public User getCurrent() {return current;}
    public void setCurrent(User current) {
        this.current = current;
    }

    public UserLController() {
    }

    public List<User> getItems() {
        if (items == null) {
            l.info("inside "+CLASSNAME+"#getItems()");            
            items = getFacade().findAll();
            l.info("************ "+items.size()+" items returned");
        }
        return items;
    }

    public String edit() {
        Integer id = current.getId();
        User user = getFacade().find(id);
        l.info("found user: "+user);
        userEController.setTheEdited(user);
        return "userEdit";
    }

    public String remove() {
        Integer id = current.getId();
        User user = getFacade().find(id);
        l.info("found user: "+user);
        getFacade().remove(user);
        return "null";
    }



}
