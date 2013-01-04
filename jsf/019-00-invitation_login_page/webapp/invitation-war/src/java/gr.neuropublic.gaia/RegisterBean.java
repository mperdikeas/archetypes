package gr.neuropublic.gaia;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable {
    
    @ManagedProperty(value="#{invId}")
    private String invId;

    private static Logger l = LoggerFactory.getLogger(RegisterBean.class); 

    public String getInvId() { return invId; }
    public void   setInvId(String invId) {
        l.info("setInvId("+invId+") called");
        this.invId = invId;
    }
}
