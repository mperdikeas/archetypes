package jpatest;
import javax.persistence.*;
import entities.A;
import java.util.logging.Logger;

public class Test {
    private static final Logger l = Logger.getLogger(Test.class.getName());
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        EntityTransaction userTransaction = em.getTransaction();
        final boolean INSERT_2ND_VALUE = false;
        if (INSERT_2ND_VALUE) {
            l.info("user transaction obtained");
            userTransaction.begin();
            l.info("transaction begun");
            A a = new A(2, "a second record");
            em.persist(a);
            l.info("object persisted");
            userTransaction.commit();
            l.info("transaction commited");
        }
        long t0 = System.currentTimeMillis();
        A aHeavy = em.find(A.class, 1);
        long t1 = System.currentTimeMillis();
        l.info("obtained in "+(t1-t0)+" ms : "+aHeavy);
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}