package mjb44.calculator_with_navigation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;

@ManagedBean(name="user")
// @RequestScoped // you see bean created every time you call a method
@SessionScoped // you see a bean created anew after the browser has been idling for the number of minutes configured in <session-config>/<session-timeout> in web.xml
public class User {
    private String name;
    private String password;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String login(){
        // Image here a database access to validate the users
        if (name.equals("tester") && password.equals("tester")){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(SessionKeys.LOGGEDIN_USER, name);
            return "success";
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(SessionKeys.ATTEMPTED_USER, name);            
            return "failure";
        }
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        if (externalContext.getSessionMap().containsKey(SessionKeys.LOGGEDIN_USER)) {
            externalContext.getSessionMap().remove(SessionKeys.LOGGEDIN_USER);
            return "login";
        }
        throw new RuntimeException("bad choreography");
    }
}
