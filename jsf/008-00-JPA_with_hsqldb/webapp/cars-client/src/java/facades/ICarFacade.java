package facades;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

import entities.Car;

public interface ICarFacade {

    List<Car> findAll();
    public Car find(Object id);
    public void edit(Car object);
    public void remove(Car object);

    @Local
    public interface ILocal extends ICarFacade {}

    @Remote
    public interface IRemote extends ICarFacade {}

}