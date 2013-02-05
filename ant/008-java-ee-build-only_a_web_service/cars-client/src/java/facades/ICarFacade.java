package facades;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;


public interface ICarFacade {


    @Local
    public interface ILocal extends ICarFacade {}

    @Remote
    public interface IRemote extends ICarFacade {}

}