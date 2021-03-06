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
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.neuropublic.gaia.invitation.api.IRegisterEJB;
import gr.neuropublic.gaia.invitation.api.InvitationStatus;


@ManagedBean
@ViewScoped
public class RegisterBean implements Serializable {
    
    private String invId;

    @EJB(beanName="RegisterEJB")
    IRegisterEJB.ILocal registerEJB;

    private static Logger l = LoggerFactory.getLogger(RegisterBean.class); 

    @javax.annotation.PostConstruct
    private void postconstructinit() {
        l.info("################\n################\n################");
    }

    public String getInvId() { return invId; }
    public void   setInvId(String invId) {this.invId = invId;}

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}

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

    
    public void init() throws SQLException {
        if (invId!=null)
            setEmail(registerEJB.getEmailAssocWithInvitation(this.invId));
    }

    public boolean getInvitationCurrent() throws SQLException {
        return invitationStatusIs(InvitationStatus.CURRENT);
    }

    public boolean getInvitationExpired() throws SQLException {
        return invitationStatusIs(InvitationStatus.EXPIRED);
    }

    public boolean getInvitationNotFound() throws SQLException {
        return invitationStatusIs(InvitationStatus.NOT_FOUND);
    }

    public boolean getInvitationAlreadyAccepted() throws SQLException {
        return invitationStatusIs(InvitationStatus.ALREADY_ACCEPTED);
    }

    private boolean invitationStatusIs(InvitationStatus is) throws SQLException {
        assertNotNull(invId);
        return registerEJB.invitationStatus(this.invId).equals(is);
    }

    public String register() {
        assertNotNull(invId);
        try {
            registerEJB.register(invId, email, firstname, surname);
            return "successfulRegistration";
        } catch (SQLException s) {
            return "sqlException";
        }

    }

    private void assertNotNull(Object ...objs) {
        for (Object obj : objs)
            if (obj==null) throw new RuntimeException(String.format("%s", obj));
    }

}
