package facades;

import java.util.List;
import entities.PrivateUserData;

public interface IPrivateUserDataFacade {

    List<PrivateUserData> findAll();
}