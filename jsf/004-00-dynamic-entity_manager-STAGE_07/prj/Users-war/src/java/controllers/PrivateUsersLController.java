package controllers;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class PrivateUsersLController implements Serializable {
    private final Logger l = Logger.getLogger(this.getClass().getName());

    @ManagedProperty(value="#{indexBackingBean}")
    private IndexBackingBean indexBackingBean;
    public void setIndexBackingBean(IndexBackingBean indexBackingBean) {
        this.indexBackingBean = indexBackingBean;
    }

    @ManagedProperty(value="#{employeeEController}")
    private EmployeeEController employeeEController;
    public void setEmployeeEController(EmployeeEController employeeEController) {
        this.employeeEController = employeeEController;
    }
    
    private PrivateUserData current;
    private List<PrivateUserData> items;
    public PrivateUserData getCurrent() {return current;}
    public void setCurrent(PrivateUserData current) {
        l.info("setting current to the value of: "+current);
        this.current = current;
    }

    public PrivateUsersLController() {
    }

    public static String safePrivateUserDataToString(PrivateUserData x) {
        return x==null?"null":(x.getEmployee()+":"+x.getEmployeePersonal());
    }

    public static String safeListSizeToString(List<?> aList) {
        return aList==null?"list is null":aList.size()+" elements";
    }

    public String prepareEdit() {
        String name = current.getEmployee();
        PrivateUserData privateUserData = UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).find(name);
        l.info("found privateUserData: "+privateUserData);
        employeeEController.setTheEditedEmployee(privateUserData);
        return "EmployeeEdit";
    }

    public String destroy() {
        String name = current.getEmployee();
        PrivateUserData privateUserData = UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).find(name);
        l.info("found privateUserData: "+privateUserData);
        UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).remove(privateUserData);
        return "null";
    }


    public List<PrivateUserData> getItems() {
        if (items == null) {
            l.info("inside "+PrivateUsersLController.class.getName()+"#getItems()");            
            items = UsersFacadeUtil.getFacade(indexBackingBean.getJdbcUrl()).findAll();
            l.info("**************** "+items.size()+" items returned");
        }
        return items;
    }



}
