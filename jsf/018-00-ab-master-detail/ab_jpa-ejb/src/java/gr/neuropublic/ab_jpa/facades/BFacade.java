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
@Local (IBFacade.ILocal.class)
@Remote(IBFacade.IRemote.class)
public class BFacade extends AbstractFacade<B> implements IBFacade.ILocal, IBFacade.IRemote {
    @PersistenceContext(unitName = "ab_jpaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private final Logger logger = LoggerFactory.getLogger(BFacade.class);

    public BFacade() {
        super();
    }



    @Override
    public B initRow() {
        return new B(getNextSequenceValue());
    }

    public int getNextSequenceValue()
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery("SELECT nextval('b_id_seq')");
        BigInteger nextValBI = (BigInteger) query.getSingleResult();

        return nextValBI.intValue();
    }

    public List<B> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder) {
        Object o = em.getDelegate();
        logger.info(String.format("AFacade() EEEEEEEEEEEEEEEE: this is %d, em is %d, real em is %d class name is: %s", System.identityHashCode(this), System.identityHashCode(em), System.identityHashCode(o), o.getClass().getName()));
        return super.findAllByCriteriaRange(params, range, recordCount, sortField, sortOrder);
    }

    public List<B> findAll() {
        List<B> ret = (List<B>) em.createNamedQuery("B.findAll").
            getResultList();

        return ret;
    }

    public int delAll() {                                                                                                             
        Query deleteQuery = em.createNamedQuery("B.delAll");
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public B findById(Integer id) {
        B exactlyOne = getOneAndOnly( (List<B>) em.createNamedQuery("B.findById").
            setParameter("id", id).
            getResultList());
        return exactlyOne;
    }

    public int delById(Integer id) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("B.delById").
            setParameter("id", id);
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public List<B> findByB1(String b1) {
        List<B> ret = (List<B>) em.createNamedQuery("B.findByB1").
            setParameter("b1", b1).
            getResultList();

        return ret;
    }

    public int delByB1(String b1) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("B.delByB1").
            setParameter("b1", b1);
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public List<B> findByA(A a) {
        List<B> ret = (List<B>) em.createNamedQuery("B.findByA").
            setParameter("a", a).
            getResultList();

        return ret;
    }

    public List<B> findByA(Integer a) {
        List<B> ret = (List<B>) em.createNamedQuery("B.findByA").
            setParameter("a", new A(a)).
            getResultList();

        return ret;
    }

    public int delByA(A a) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("B.delByA").
            setParameter("a", a);
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public int delByA(Integer a) {                                                                                                             
        Query deleteQuery = em.createNamedQuery("B.delByA").
            setParameter("a", new A(a));
        return deleteQuery.executeUpdate();                                                                                                         
    }

    public List<B> findB(String b1, A a, int recordLimit) {
        logger.info("findB("+ " b1:"+b1 + " a:"+a +")");
        StringBuffer query = new StringBuffer();
        query.append("SELECT x FROM B x ");
        List<String> whereCriteria = new ArrayList<String>();
        if (b1 != null) {
            whereCriteria.add("(x.b1 like :b1)");
        }
        if (a != null) {
            whereCriteria.add("(x.a = :a)");
        }
        
        if (whereCriteria.size() > 0)
    		query.append(" WHERE ");
        
        for (int i = 0; i < whereCriteria.size(); i++) {
            if (i > 0) { query.append(" AND "); }
                query.append(whereCriteria.get(i));
        }

        Query q = em.createQuery(query.toString());

        if (b1 != null) {
            q.setParameter("b1", b1+"%"); 
        }
        if (a != null) {
            q.setParameter("a", a); 
        }

        if (recordLimit  > 0)
        {
            q.setMaxResults(recordLimit);
        }

        List<B> ret = (List<B>)q.getResultList();
        logger.info("findB() return :" + ret.size() + " items");

        return ret;
    }

}