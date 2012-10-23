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
@ViewScoped 
public class BeanA implements Serializable {

    private static final Logger l = Logger.getLogger(BeanA.class.getName());

    private Integer a;
    public Integer getA() {
        l.info("<<<<<<<<<<<<<< BeanA getter");
        return a ;
    }
    public void setA(Integer a) {
        l.info(">>>>>>>>>>>>>> BeanA setter ");
        this.a = a ;
    }

    public BeanA() {
        l.info("************ BeanA constructor ************");
        this.a = 0;
    }

    public void samePage() {l.info("samePage() called"); a++; }

}