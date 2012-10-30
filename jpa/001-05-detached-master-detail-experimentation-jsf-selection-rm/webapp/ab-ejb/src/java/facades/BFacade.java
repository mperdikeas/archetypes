package facades;

import base.AbstractFacade;
import mutil.jpapersutil.Select;
import mutil.jpapersutil.QualifiedResultList;
import mutil.jpapersutil.JPQLUtil;
import javax.ejb.Stateless;
import javax.ejb.Stateful;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import mutil.base.Pair;

import entities.B;

@Stateless
@Local(IBFacade.ILocal.class)
@Remote(IBFacade.IRemote.class)
public class BFacade extends AbstractFacade<B> implements IBFacade.ILocal {
    @PersistenceContext(unitName = "abPU")
    private EntityManager em;

    private static final Logger l = Logger.getLogger(BFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
