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

import entities.*;
import facades.*;


@ManagedBean
@RequestScoped
public class UserLController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String CLASSNAME=UserLController.class.getName();

    /*@ManagedProperty(value="#{userEController}")    
    private UserEController userEController;
    public void setUserEController(UserEController userEController) {
        this.userEController = userEController;
    }*/

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

    private static BooleanToggler booleanToggler = new BooleanToggler(); 

    public String edit() {
        Integer id = current.getId();
        User user = getFacade().find(id);
        l.info("found user: "+user);
        // userEController.setTheEdited(user);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("theEditedId", user.getId());
        boolean redirect = booleanToggler.getValueAndToggle(); // statically set to false to kill the bug
        l.info("redirecting = "+redirect);
        return "userEdit"+(redirect?"?faces-redirect=true":"");
    }


    public String create() {
        User user = new User();
        user.setFirstname("");
        user.setSurname("");
        user.setAge(0);
        final int MAX_RETRIES = 3;
        boolean haveSucceeded = false;
        int _i = 0 ;
        while ((!haveSucceeded) && (_i++ < MAX_RETRIES)) {
            try {
                user.setId(getFacade().maxId()+1);
                getFacade().create(user);
                haveSucceeded = true;
            } catch (javax.ejb.EJBTransactionRolledbackException exc) {
                l.info("ConstraintValidationException - retrying");   
            }
        }
        Util.panicIf(!haveSucceeded);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("theEditedId", user.getId());
        boolean redirect = booleanToggler.getValueAndToggle();
        l.info("redirecting = "+redirect);
        return "userEdit"+(redirect?"?faces-redirect=true":"");
    }


    private static BooleanToggler booleanToggler2 = new BooleanToggler(); 

    public String remove() {
        Integer id = current.getId();
        User user = getFacade().find(id);
        l.info("found user: "+user+" which I am about to remove");
        getFacade().remove(user);
        if (booleanToggler2.getValueAndToggle()) {
            l.info("you should see the list updated");
            items = null;
        } else {
            l.info("I am not setting items to null hence not triggering a fetch from the database during the RENDER_RESPONSE phase");
        }
        return "null";
    }

}
