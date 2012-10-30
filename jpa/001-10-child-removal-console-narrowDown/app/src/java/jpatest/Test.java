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


    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        A a = em.find(A.class, 1);
        B b = getARandomChildOfA(a);


        em.remove(em.merge(b));
        // b.setAId(null); // only this works

        entityTransaction.commit();

        em.close();
        entityManagerFactory.close();
    }
}