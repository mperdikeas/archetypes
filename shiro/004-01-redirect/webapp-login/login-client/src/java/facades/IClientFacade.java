package facades;

import java.util.List;
import entities.Client;
import javax.ejb.Remote;
import javax.ejb.Local;

public interface IClientFacade {

    public Client getClientByClientName(String clientName);

    @Local
    public interface ILocal extends IClientFacade {}

    @Remote
    public interface IRemote extends IClientFacade {}

}