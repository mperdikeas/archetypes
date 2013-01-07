package gr.neuropublic.gaia.invitation.api;

import java.sql.Timestamp;
import java.sql.SQLException;

import javax.ejb.Local;
import javax.ejb.Remote;

public interface IRegisterEJB {

    public boolean register(String email, String firstname, String lastname) throws SQLException;
    public InvitationStatus invitationStatus(String invId)                   throws SQLException ;
    public String getEmailAssocWithInvitation(String invId)                  throws SQLException;

    @Local
    public interface ILocal extends IRegisterEJB {}

    @Remote
    public interface IRemote extends IRegisterEJB {}

}
