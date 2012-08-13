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

import entities.User;

@Stateless(name="userFacade")
@Local(IUserFacadeLocal.class)
@Remote(IUserFacadeRemote.class)
public class UserFacade extends AbstractFacade<User> implements IUserFacadeLocal, IUserFacadeRemote {
    @PersistenceContext(unitName = "allusersdbPU")
    private EntityManager em;


    private final Logger l = Logger.getLogger(this.getClass().getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public UserFacade() {
        super(User.class);
    }

    
}
