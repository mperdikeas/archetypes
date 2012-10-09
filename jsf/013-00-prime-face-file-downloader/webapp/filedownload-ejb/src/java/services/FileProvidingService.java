package services;

import java.io.InputStream;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mutil.base.FileUtil;
import mutil.base.ExceptionAdapter;

@Stateless
@Local (IFileProvidingService.ILocal.class)
@Remote(IFileProvidingService.IRemote.class)
public class FileProvidingService implements IFileProvidingService.ILocal, IFileProvidingService.IRemote{

    private static Logger l = LoggerFactory.getLogger(FileProvidingService.class);    


    
    public InputStream getStream() {
        return null;
    }


}