package facades;

import java.util.List;
import entities.Car;

public interface ICarFacade {

    List<Car> findAll();
    public Car find(Object id);
    public void edit(Car object);
    public void remove(Car object);
}