package controllers;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
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
@SessionScoped
public class EmployeeEController implements Serializable {

    @ManagedProperty(value="#{indexBackingBean}")
    private IndexBackingBean indexBackingBean;
    public void setIndexBackingBean(IndexBackingBean indexBackingBean) {
        this.indexBackingBean = indexBackingBean;
    }


    private final Logger l = Logger.getLogger(this.getClass().getName());
    private static final String className = EmployeeEController.class.getName();
    private PrivateUserData theEditedEmployee;

    public PrivateUserData getTheEditedEmployee() {
        l.info("returning the edited employee: "+theEditedEmployee);   
        return theEditedEmployee;
    }
    public void setTheEditedEmployee(PrivateUserData theEditedEmployee) {
        this.theEditedEmployee = theEditedEmployee;
        l.info("the edited employee set at "+className+ " and is: "+this.theEditedEmployee);
    }

    public EmployeeEController() {
    }
    
    public void save() {
        l.info("inside EmployeeEController, saving employee="+theEditedEmployee);
        UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).edit(theEditedEmployee);
    }
    
    public void remove() {
        UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).remove(theEditedEmployee);
    }
}
