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
import mutil.jpapersutil.QualifiedResultList;

import entities.Client;

@Stateless
@Local (IClientFacade.ILocal.class)
@Remote(IClientFacade.IRemote.class)
public class ClientFacade extends AbstractFacade<Client> implements IClientFacade {
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

    public Client getClientByClientName(String clientName) {
        Pair<String, Map<String, Object>> jpql_and_params = Select.from(Client.class).where().f("clientName").is(clientName).end().getQuery();
        l.info("ClientFacade::getClientByClientName("+clientName+")");
        QualifiedResultList<Client> clients = JPQLUtil.getResults(em, jpql_and_params, 1);
        if (clients.theresMore) throw new RuntimeException();
        return clients.data.get(0);
    }
    
}
