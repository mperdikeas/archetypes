package gr.neuropublic.ab_jpa.facades;

import java.util.List;

import gr.neuropublic.base.AbstractFacade;
import gr.neuropublic.ab_jpa.entities.*;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.persistence.Query;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static mutil.base.ListUtil.getOneAndOnly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Stateless
@Local (IAFacade.ILocal.class)
@Remote(IAFacade.IRemote.class)
public class AFacade extends AbstractFacade<A> implements IAFacade.ILocal, IAFacade.IRemote {
    @PersistenceContext(unitName = "ab_jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final Logger logger = LoggerFactory.getLogger(AFacade.class);

    public AFacade() {
        super();
    }


    @Override
    public A initRow() {
        return new A(getNextSequenceValue());
    }


    public List<A> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder) {
        Object o = em.getDelegate();
        logger.info(String.format("AFacade() EEEEEEEEEEEEEEEE: this is %d, em is %d, real em is %d class name is: %s", System.identityHashCode(this), System.identityHashCode(em), System.identityHashCode(o), o.getClass().getName()));
        int millisToWait = 0;
        logger.info(String.format("sleeping for %d secs", millisToWait/1000));
        try { Thread.sleep(millisToWait); } catch (Exception e) {}
        logger.info("just woke up");
        return super.findAllByCriteriaRange(params, range, recordCount, sortField, sortOrder);
    }

    public int getNextSequenceValue()
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery("SELECT nextval('a_id_seq')");
        BigInteger nextValBI = (BigInteger) query.getSingleResult();

        return nextValBI.intValue();
    }

    public List<A> findAll() {
        List<A> ret = (List<A>) em.createNamedQuery("A.findAll").
            getResultList();

        return ret;
    }

    public int delAll() {                                                                                                             
        Query deleteQuery = em.createNamedQuery("A.delAll");
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public A findById(Integer id) {
        logger.info(String.format("A :: findById em is: %d class name is %s", System.identityHashCode(em), em.getClass().getSimpleName()));
        A exactlyOne = getOneAndOnly( (List<A>) em.createNamedQuery("A.findById").
            setParameter("id", id).
            getResultList());
        return exactlyOne;
    }

    public int delById(Integer id) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("A.delById").
            setParameter("id", id);
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public List<A> findByA1(String a1) {
        List<A> ret = (List<A>) em.createNamedQuery("A.findByA1").
            setParameter("a1", a1).
            getResultList();

        return ret;
    }

    public int delByA1(String a1) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("A.delByA1").
            setParameter("a1", a1);
        return deleteQuery.executeUpdate();                                                                                                         
    }

}