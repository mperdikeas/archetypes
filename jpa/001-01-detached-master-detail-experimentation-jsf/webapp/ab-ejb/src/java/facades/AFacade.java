package facades;

import base.AbstractFacade;
import mutil.jpapersutil.Select;
import mutil.jpapersutil.QualifiedResultList;
import mutil.jpapersutil.JPQLUtil;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import mutil.base.Pair;

import entities.A;

@Stateless
@Local(IAFacadeLocal.class)
@Remote(IAFacadeRemote.class)
public class AFacade extends AbstractFacade<A> implements IAFacadeLocal {
    @PersistenceContext(unitName = "abPU")
    private EntityManager em;

    private static final String CLASSNAME = AFacade.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AFacade() {
        super(A.class);
    }

    
}
