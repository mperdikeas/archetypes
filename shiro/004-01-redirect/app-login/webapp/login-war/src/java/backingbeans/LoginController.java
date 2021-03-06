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
import javax.servlet.http.HttpSession;

import facades.IClientFacade;
import entities.Client;

@ManagedBean
@RequestScoped
public class LoginController implements Serializable {

    @EJB(beanName = "ClientFacade")
    private IClientFacade.ILocal clientFacade;


    private static final String CLASSNAME=LoginController.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    private String client;
    
    public String getClient() {
        return client;
    }
    
    public void setClient(String client) {
        this.client = client;
    }

    private String username;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    private String password;
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws java.io.IOException {
        l.info("looking for client with client name=  "+getClient());
        Client client = clientFacade.getClientByClientName(getClient());
        l.info("client returned is: "+client);
        String uri=String.format("http://%s?username=%s&pass=%s", client.getCoords(), getUsername(), getPassword());
        FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
        return null;
    }


}
