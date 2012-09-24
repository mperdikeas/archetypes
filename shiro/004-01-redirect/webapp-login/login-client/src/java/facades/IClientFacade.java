package facades;

import java.util.List;
import entities.Client;

public interface IClientFacade {

    public Client find(Object id);
}