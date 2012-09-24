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

import entities.Client;

@Stateless
@Local(IClientFacadeLocal.class)
@Remote(IClientFacadeRemote.class)
public class ClientFacade extends AbstractFacade<Client> implements IClientFacadeLocal {
    @PersistenceContext(unitName = "clientsPU")
    private EntityManager em;

    private static final String CLASSNAME = ClientFacade.class.getName();
    private static final Logger l = Logger.getLogger(CLASSNAME);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    
}
