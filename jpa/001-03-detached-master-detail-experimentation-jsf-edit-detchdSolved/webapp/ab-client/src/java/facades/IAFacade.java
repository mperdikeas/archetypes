package facades;

import javax.ejb.Local;
import javax.ejb.Remote;

import java.util.List;
import entities.A;
import base.IFacade;


public interface IAFacade extends IFacade<A> {

    @Local
    public interface ILocal extends IAFacade {}

    @Remote
    public interface IRemote extends IAFacade {}

}