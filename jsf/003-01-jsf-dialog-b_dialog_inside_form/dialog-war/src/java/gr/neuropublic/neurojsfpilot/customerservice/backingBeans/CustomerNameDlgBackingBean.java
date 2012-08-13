package gr.neuropublic.neurojsfpilot.customerservice.backingBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
public class CustomerNameDlgBackingBean {
    private static final Logger l = Logger.getLogger(CustomerNameDlgBackingBean.class.getName());

    @ManagedProperty(value = "#{customerBackingBean}")
    private CustomerBackingBean customerBackingBean;

    public CustomerBackingBean getCustomerBackingBean() {
        return customerBackingBean;
    }

    public void setCustomerBackingBean(CustomerBackingBean customerBackingBean) {
        this.customerBackingBean = customerBackingBean;
    }

    private String name;
    public String getName() {
        l.info(CustomerNameDlgBackingBean.class.getName()+"#getName() returning: "+name);
        return name;
    }
    public void setName(String name) {
        l.info("CustomerNameDlgBackingBean#setName("+name+")");
        this.name = name;
        getCustomerBackingBean().setTentativeName(name);
    }



    public void nameChange(ValueChangeEvent e) {
        l.info("value change detected: from: "+e.getOldValue()+" to: "+e.getNewValue());
    }


}