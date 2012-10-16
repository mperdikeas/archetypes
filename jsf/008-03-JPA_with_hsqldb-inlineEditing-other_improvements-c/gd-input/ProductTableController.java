/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

import gr.neuropublic.ejbs.base.AbstractFacade;
import gr.neuropublic.jsf.base.CRUDTableController;
import gr.neuropublic.neurojsfpilot.customerservice.entities.Product;
import gr.neuropublic.neurojsfpilot.customerservice.facades.ProductFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author g_dimitriadis
 */
@ManagedBean(name = "productTableController")
@ViewScoped
public class ProductTableController extends CRUDTableController<Product> implements Serializable {

    @EJB
    private ProductFacade productFacade;

    @Override
    protected AbstractFacade getFacade() {
        return productFacade;
    }
}