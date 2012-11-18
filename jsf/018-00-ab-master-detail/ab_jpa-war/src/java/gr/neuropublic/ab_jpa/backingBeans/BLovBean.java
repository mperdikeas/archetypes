package gr.neuropublic.ab_jpa.backingBeans;


import gr.neuropublic.base.IFacade;
import gr.neuropublic.ab_jpa.entities.*;
import gr.neuropublic.ab_jpa.facades.*;
import gr.neuropublic.jsf.base.LOVController;
import gr.neuropublic.jsf.base.EntityBasedController;
import gr.neuropublic.jsf.util.*;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ManagedBean(name = "bLovBean")
@SessionScoped
public class BLovBean extends LOVController<B> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(BLovBean.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IBFacade.ILocal ejbFacade;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }

    private Boolean b1_rendered=false;
    public Boolean getB1_rendered() {
        return b1_rendered;
    }
    public void setB1_rendered(Boolean vl) {
        this.b1_rendered = vl;
    }
    private Boolean a_a1_rendered=false;
    public Boolean getA_a1_rendered() {
        return a_a1_rendered;
    }
    public void setA_a1_rendered(Boolean vl) {
        this.a_a1_rendered = vl;
    }

}