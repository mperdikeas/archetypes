package facades;

import java.util.List;
import entities.Car;

public interface IFileConsumingService {

    public void consume (byte[] fileBytes) ;


    @Remote
    public interface IRemote extends IFileConsumingService;

    @Remote
    public interface ILocal extends IFileConsumingService;

}