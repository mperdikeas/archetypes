package facades;

import javax.ejb.Local;
import javax.ejb.Remote;

import java.util.List;
import entities.B;
import base.IFacade;


public interface IBFacade extends IFacade<B> {

    @Local
    public interface ILocal extends IBFacade {}

    @Remote
    public interface IRemote extends IBFacade {}

}