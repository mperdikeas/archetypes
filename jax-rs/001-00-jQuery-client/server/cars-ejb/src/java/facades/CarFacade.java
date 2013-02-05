package facades;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import mutil.base.Pair;


@Stateless
@Local(ICarFacade.ILocal.class)
@Remote(ICarFacade.IRemote.class)
public class CarFacade implements ICarFacade.ILocal {

    private static final String CLASSNAME = CarFacade.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);


    
}
