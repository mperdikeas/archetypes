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
public class BeanB implements Serializable {

    private static final Logger l = Logger.getLogger(BeanB.class.getName());

    private Integer b;
    public Integer getB() {
        l.info("<<<<<<<<<<<<<< BeanB getter");
        return b ;
    }
    public void setB(Integer b) {
        l.info(">>>>>>>>>>>>>> BeanB setter ");
        this.b = b ;
    }

    public BeanB() {
        l.info("************ BeanB constructor ************");
        this.b = 0;
    }

}