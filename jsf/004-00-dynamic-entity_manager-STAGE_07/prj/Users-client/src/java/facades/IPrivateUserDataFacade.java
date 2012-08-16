package facades;

import java.util.List;
import entities.PrivateUserData;

public interface IPrivateUserDataFacade {

    List<PrivateUserData> findAll();
    public PrivateUserData find(Object id);
    public void edit(PrivateUserData object);
    public void remove(PrivateUserData object);
}