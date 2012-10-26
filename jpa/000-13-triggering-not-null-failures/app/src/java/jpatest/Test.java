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

        
        A a = em.find(A.class, 1);
        a.setA1(null);
        entityTransaction.begin();
        entityTransaction.commit();

        l.info("just commited the transaction");
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}