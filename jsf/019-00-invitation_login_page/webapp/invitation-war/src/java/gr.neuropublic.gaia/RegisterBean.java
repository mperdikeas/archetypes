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

    private String firstname;
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    private String surname;
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    private int age;
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    private String profession;
    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    private String street;
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    private int streetNo;
    public int getStreetNo() { return streetNo; }
    public void setStreetNo(int streetNo) { this.streetNo = streetNo; }

    public void register() {
        // invoke operation on RegisterEJB
    }

}
