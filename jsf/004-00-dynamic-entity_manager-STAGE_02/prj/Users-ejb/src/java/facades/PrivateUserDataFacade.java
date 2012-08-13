package facades;

import base.AbstractFacade;
import mutil.jpapersutil.Select;
import mutil.jpapersutil.QualifiedResultList;
import mutil.jpapersutil.JPQLUtil;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import mutil.base.Pair;

import entities.PrivateUserData;

@Stateless
public class PrivateUserDataFacade extends AbstractFacade<PrivateUserData> {
    @PersistenceContext(unitName = "user01dbPU")
    private EntityManager em;


    private final Logger l = Logger.getLogger(this.getClass().getName());

    public String foo() { return "foo"; }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public PrivateUserDataFacade() {
        super(PrivateUserData.class);
    }

    
}
