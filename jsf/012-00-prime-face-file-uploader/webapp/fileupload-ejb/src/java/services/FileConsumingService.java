package services;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.CharArrayReader;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import mutil.base.FileUtil;
import mutil.base.ExceptionAdapter;

@Stateless
@Local (IFileConsumingService.ILocal.class)
@Remote(IFileConsumingService.IRemote.class)
public class FileConsumingService implements IFileConsumingService.ILocal, IFileConsumingService.IRemote{

    private static Logger l = LoggerFactory.getLogger(FileConsumingService.class);    



    public String ping() {return "pong";}
    
    public void consume (byte[] fileBytes) {
        l.info(String.format("the uploaded file has apparently a size of %d bytes", fileBytes.length));
    }


}