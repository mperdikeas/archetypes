package facades;

import java.util.List;
import entities.A;

public interface IAFacade {

    List<A> findAll();
    public A find(Object id);
    public void edit(A object);
    public void remove(A object);
}