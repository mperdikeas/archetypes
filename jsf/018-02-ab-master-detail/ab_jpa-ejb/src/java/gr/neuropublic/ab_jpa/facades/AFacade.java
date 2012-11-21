package gr.neuropublic.ab_jpa.facades;

import java.util.List;

import gr.neuropublic.base.AbstractFacade;
import gr.neuropublic.ab_jpa.entities.*;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Query;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.*;
import static mutil.base.ListUtil.getOneAndOnly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateful
@Local (IAFacade.ILocal.class)
@Remote(IAFacade.IRemote.class)
public class AFacade extends AbstractFacade<A> implements IAFacade.ILocal, IAFacade.IRemote {


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final Logger logger = LoggerFactory.getLogger(AFacade.class);



    @Override
    public A initRow() {
        A a = new A(getNextSequenceValue());
        a.setBaCollection(new ArrayList()); // Initialize the detail collection with a new ArrayList
        return a;
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
    
    
//    // TODO this method should be simplified
//    public List<A> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder) {
//
//        //Initialize query String
//        StringBuilder queryStr = new StringBuilder("SELECT a FROM A a where SIZE(a.baCollection) <=100 ");
//        StringBuilder countQueryStr = new StringBuilder("SELECT count(e) FROM A e ");
//
//        //Create queries
//        Query query = getEntityManager().createQuery(queryStr.toString());
//        Query countQuery = getEntityManager().createQuery(countQueryStr.toString());
//
//        //Find the total record count and add to the row count
//        if (recordCount != null) {
//            recordCount[0] += ((Long) countQuery.getSingleResult()).intValue();
//        }
//
//        //Set up the pagination range
//        if (range != null) {
//            query.setMaxResults(range[1] - range[0]);
//            query.setFirstResult(range[0]);
//        }
//
//        return query.getResultList();
//    }
    


}