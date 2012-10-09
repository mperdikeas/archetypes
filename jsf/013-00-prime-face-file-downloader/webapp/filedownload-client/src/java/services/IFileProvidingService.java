package services;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;

import java.io.InputStream;

public interface IFileProvidingService {

    public InputStream getStream();


    @Remote
    public interface IRemote extends IFileProvidingService {}

    @Remote
    public interface ILocal extends IFileProvidingService {}

}