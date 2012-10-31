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

import facades.IAFacade.ILocal;
import facades.IAFacade;
import entities.A;
import base.IFacade;

@ManagedBean
@ViewScoped
public class ALController implements Serializable {

    private static final String CLASSNAME=ALController.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    @EJB(beanName = "AFacade")
    private IAFacade.ILocal aFacade;

    public String gotoCreate() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("create-mode", true);
        return "aedit";
    }

    public String gotoEdit(A item) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("create-mode", false);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("master-record", item);
        return "aedit";
    }

    public void remove(A item) {
        l.info("about to call remove with object: "+item);
        aFacade.remove(item);
        l.info("done calling remove");
        loadDatabase = true; // without this it's not working
        l.info("done flagging database for reload");
    }

    private boolean loadDatabase = true;

    List<A> items;
    public void synchItemsFromDB() {
        items = aFacade.findAll();
    }

    public List<A> getItems() { 
        if (loadDatabase) {
            synchItemsFromDB();
            loadDatabase = false;
        }
        return items;
    }

}

