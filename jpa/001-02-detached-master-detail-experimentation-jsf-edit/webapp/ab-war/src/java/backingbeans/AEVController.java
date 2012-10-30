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

import facades.IAFacadeLocal;
import facades.IAFacade;
import entities.A;
import base.IFacade;

@ManagedBean
@ViewScoped
public class AEVController implements Serializable {

    private static final Logger l = Logger.getLogger(AEVController.class.getName());

    @EJB(beanName = "AFacade")
    private IAFacadeLocal aFacade;

    public String gotoBs() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("the-master-record", current);
        return "ablist";
    }


    @PostConstruct
    public void initBasedOnMode() {
        boolean createMode = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("create-mode");
        if (createMode) {
            current = new A();
        } else {
            current = (A) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("master-record");
        }
    }


    private A current;

    public A getCurrent() { return current; }

}

