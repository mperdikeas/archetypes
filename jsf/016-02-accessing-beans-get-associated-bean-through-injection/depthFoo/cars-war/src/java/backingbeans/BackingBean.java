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


import mutil.base.ListUtil;

@ManagedBean
@RequestScoped // you can't change that to ViewScoped or you get the exception:
               //  The scope of the object referenced by expression #{beanB}, request, is shorter than the referring managed beans (backingBean) scope of view
public class BackingBean implements Serializable {

    private static final Logger l = Logger.getLogger(BeanA.class.getName());

    @ManagedProperty("#{beanA}")
    BeanA beanA;
    public BeanA getBeanA() { return beanA; }
    public void setBeanA(BeanA beanA) { this.beanA = beanA; }

    @ManagedProperty("#{beanB}")
    BeanB beanB;
    public BeanB getBeanB() { return beanB; }
    public void setBeanB(BeanB beanB) { this.beanB = beanB; }


    public Integer getA() {
        l.info("<<<<<<<<<<<<<< BackingBean#a getter");
        return beanA.getA();
    }

    public Integer getB() {
        l.info("<<<<<<<<<<<<<< BackingBean#b getter");
        return beanB.getB();
    }

    public BackingBean() {
        l.info("************ BackingBean constructor ************");
    }

    public void samePage() {
        beanA.increment();
        beanB.increment();
    }

    public String samePageAction() {
        beanA.increment();
        beanB.increment();
        return null;
    }

}