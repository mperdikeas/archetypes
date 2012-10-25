package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class Test {
    private static final Logger l = Logger.getLogger(Test.class.getName());

    private static B findInA(A a, int idToLookFor) {
        Set<B> bs = a.getBCollection();
        for (Iterator<B> bsI = bs.iterator(); bsI.hasNext() ;) {
            B b = bsI.next();
            if (b.getId()==idToLookFor)
                return b;
        }
        return null;
    }

    private static B getARandomChildOfA(A a) {
        Object[] bs = a.getBCollection().toArray();
        int randomI = (int) (System.currentTimeMillis() % (bs.length));
        return (B) bs[randomI];
    }

    public static void mangleATree(EntityManager em, int i, boolean manglingMethodIsEM) {
        A a1 = em.find(A.class, i);
        l.info("A instance with key of " + i + " starts as: " + a1);
        String newValue = "G"+a1.getA1();
        a1.setA1( newValue.substring(0, newValue.length()>50?50:newValue.length()) );
        B a1b = getARandomChildOfA(a1);
        if (manglingMethodIsEM) {
            l.info("removing a child using EntityManager");
            em.remove(a1b);
        }
        else {
            l.info("removing a child using collection removal");
            a1.removeB(a1b);
        }
    }

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

        mangleATree(em, 1, true);
        mangleATree(em, 2, false);

        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");

        printA(em, 1);
        printA(em, 2);

        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}