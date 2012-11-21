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


@ManagedBean(name = "aLovBean")
@SessionScoped
public class ALovBean extends LOVController<A> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(ALovBean.class);

    @EJB
    private gr.neuropublic.ab_jpa.facades.IAFacade.ILocal ejbFacade;

    @Override
    protected IFacade getFacade() {
        return ejbFacade;
    }

    private Boolean a1_rendered=false;
    public Boolean getA1_rendered() {
        return a1_rendered;
    }
    public void setA1_rendered(Boolean vl) {
        this.a1_rendered = vl;
    }

}