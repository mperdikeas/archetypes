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
@RequestScoped
public class BackingBean implements Serializable {

    private static final Logger l = Logger.getLogger(BeanA.class.getName());

    private BeanA getBeanA() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (BeanA) context.getApplication().evaluateExpressionGet(context, "#{beanA}", BeanA.class);
    }

    private BeanB getBeanB() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (BeanB) context.getApplication().evaluateExpressionGet(context, "#{beanB}", BeanB.class);
    }

    public Integer getA() {
        l.info("<<<<<<<<<<<<<< BackingBean#a getter");
        return getBeanA().getA();
    }

    public Integer getB() {
        l.info("<<<<<<<<<<<<<< BackingBean#b getter");
        return getBeanB().getB();
    }

    public BackingBean() {
        l.info("************ BackingBean constructor ************");
    }

    public void samePage() {
        getBeanA().increment();
        getBeanB().increment();
    }

    public String samePageAction() {
        getBeanA().increment();
        getBeanB().increment();
        return null;
    }

}