package facades;

import java.util.List;
import entities.A;

public interface IAFacade {

    List<A> findAll();
    public A find(Object id);
    public void persist(A object);
    public A merge(A object);
    public void remove(A object);
}