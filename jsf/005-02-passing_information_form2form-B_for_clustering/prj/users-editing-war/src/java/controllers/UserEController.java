package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

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
@ViewScoped
public class UserEController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String CLASSNAME=UserEController.class.getName();


    @EJB
    private UserFacade userFacade;
    public UserFacade getFacade() {
        return userFacade;
    }

    private User theEdited;
    public User getTheEdited() {
        l.info("getting the edited in UserEController instance: #"+this);
        if (theEdited==null) {
            l.info("the edited is null, obtaining it from flash");
            theEdited = getFacade().find(FacesContext.getCurrentInstance().getExternalContext().getFlash().get("theEditedId"));
        } else {
            l.info("the edited is not null, using that value");
        }
        return theEdited;
    }
    public void setTheEdited(User theEdited) {
        l.info("setting the edited in UserEController instance: #"+this);
        this.theEdited = theEdited;
    }

    public UserEController() {
    }

    public String save() {
        getFacade().edit(theEdited);
        return "null";
    }

}
