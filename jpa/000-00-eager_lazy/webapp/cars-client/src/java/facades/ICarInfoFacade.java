package facades;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

import entities.CarInfo;

public interface ICarInfoFacade {

    List<CarInfo> findAll();

    public void    create (CarInfo object);
    public void    edit   (CarInfo object);
    public CarInfo find   (Object id);
    public void    remove (CarInfo object);

    @Local
    public interface ILocal extends ICarInfoFacade {}

    @Remote
    public interface IRemote extends ICarInfoFacade {}

}