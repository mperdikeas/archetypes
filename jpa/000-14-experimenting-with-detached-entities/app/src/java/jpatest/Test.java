package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class Test {
    private static final Logger l = Logger.getLogger(Test.class.getName());

    public static void printA(EntityManager em, int i) {
        A a = em.find(A.class, i);
        l.info("A instance with key: "+i+" is now: "+a);
    }

    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        EntityTransaction entityTransaction = em.getTransaction();


        A a = new A(1, "doesn't-make anydif");
        boolean exceptionThrown = false;
        try {
            em.remove(a);        // this line throws an exception complaining about a detached entity
                                 // being removed (as well as it should)
        } catch (RuntimeException e) {
            l.info("this doesn't work cause the entity is detached");
            exceptionThrown = true;
        }
        if (!exceptionThrown) throw new RuntimeException("exception should've been thrown!");
        em.remove(em.merge(a));

        em.getEntityManagerFactory().getCache().evictAll(); // strangly evictAll doesn't cause the EntityManager to go back to the database on the next find
        final boolean ClearCausesEMToGoBackToDB = true;
        if (!ClearCausesEMToGoBackToDB) em.clear(); // but em.clear does, so you can't call it, if you set the bool to false you see exception on line 45

        A canItBeFound = em.find(A.class, 1);
        if (canItBeFound==null)
            l.info("entity properly removed the second time after merging");
        else
            throw new RuntimeException("showcase bug"); // line 45

        A a2 = em.find(A.class, 2);
        em.detach(a2);
        boolean foundDetachedException = false;
        try {
            em.remove(a2);
        } catch (RuntimeException re) {
            foundDetachedException = true;
        }
        if (!foundDetachedException) throw new RuntimeException("found-detached exception should've been thrown");
        
        em.remove(em.merge(a2));
        if (em.find(A.class, 2)!=null)
            throw new RuntimeException("found-detached-merged-removed entity still found!");
        else
            l.info("entity properly removed");
        

        l.info("going to sleep for 10 seconds");
        Thread.sleep(10*1000);
        entityTransaction.begin();     // this line and the next are absolutely necessary as otherwise
        entityTransaction.commit();    // the entities are only removed in the EntityManager but not in the database.

        l.info("just commited the transaction");
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}