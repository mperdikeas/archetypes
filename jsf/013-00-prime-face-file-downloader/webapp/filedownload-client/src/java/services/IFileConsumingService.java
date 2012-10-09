package services;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;

public interface IFileConsumingService {

    public void consume (byte[] fileBytes) ;


    @Remote
    public interface IRemote extends IFileConsumingService {}

    @Remote
    public interface ILocal extends IFileConsumingService {}

}