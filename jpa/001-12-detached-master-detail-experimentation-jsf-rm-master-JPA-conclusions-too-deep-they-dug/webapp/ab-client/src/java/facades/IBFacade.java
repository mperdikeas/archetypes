package facades;

import javax.ejb.Local;
import javax.ejb.Remote;

import java.util.List;
import entities.B;
import entities.A;
import base.IFacade;


public interface IBFacade extends IFacade<B> {

    @Local
    public interface ILocal extends IBFacade {}

    @Remote
    public interface IRemote extends IBFacade {}

    public void remove(A masterRecord, B b);

}