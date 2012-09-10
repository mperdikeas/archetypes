package facades;


import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import mutil.base.ListUtil;

import base.AbstractFacade;
import entities.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "jsf005dbPU")
    private EntityManager em;

    private final Logger l = Logger.getLogger(this.getClass().getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public int maxId() {
        TypedQuery<Integer> query = getEntityManager().createNamedQuery("User.maxId", Integer.class);
        List<Integer> results = query.getResultList();
        int retValue = ListUtil.getOneAndOnly(results);
        l.info(String.format("maximum id calculated as: %d", retValue));
        return retValue;
    }

}
