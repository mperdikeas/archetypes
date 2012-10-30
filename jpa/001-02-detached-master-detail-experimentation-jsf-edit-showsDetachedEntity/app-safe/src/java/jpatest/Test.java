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


    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        A a1 = new A(4, "foo");
        A a2 = em.find(A.class, 1);
        B b = new B(6, "zoo");
        b.setAId(a1);   // this causes no problem whatsoever no matter whether EAGER or LAZY as the entity was never managed // line 37
        em.detach(a2);
        l.info("right after detachment");
        b.setAId(a2); // line 40
        l.info("right after setting father entity");
        em.merge(a1);
        em.merge(a2); // at the end of both these merges the new B row will hang under a2, i.e. line 40 overrides line 37
     
        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");


        em.close();
        entityManagerFactory.close();
    }
}