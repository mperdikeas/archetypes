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
    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        EntityTransaction entityTransaction = em.getTransaction();

        A a = em.find(A.class, 1);
        String newValue = "G"+a.getA1();
        a.setA1( newValue.substring(0, newValue.length()>50?50:newValue.length()) ); // line 31
        for (int i = 0 ; i < 100 ; i++) {
            B aB = em.find(B.class, 2);
            l.info("found a B with hash code: "+System.identityHashCode(aB));
        }
        B b2a = em.find(B.class, 2);
        l.info("found b2a:" + b2a+" with system hash code: "+System.identityHashCode(b2a));
        B aB = findInA(a, 2);
        l.info("a's b has system hash code of: "+System.identityHashCode(aB));
        em.remove(b2a);
        B aNewB = em.find(B.class, 2);
        l.info("after the removal the newly found B is: "+aNewB+" with hashcode: "+System.identityHashCode(aNewB));
        aB = findInA(a,2);
        l.info("after the removal the A's b is: "+aB+" with system hashcode: "+System.identityHashCode(aB));
        // b2b is assigned the value of 'null' so the below 5 lines fail
        // B b2b = em.find(B.class, 2);
        // l.info("b2b is: "+b2b);
        // em.merge(b2b);
        // b2b.setId(3); // you can't do that since it throws an 'org.hibernate.HibernateException: identifier of an instance of entities.B was altered from 2 to 3'
        // em.merge(b2b);
        B b2b = findInA(a, 2); // this also fails and is null due to the PreRemove JPA entity hook.
        l.info("b2b is: "+b2b);
        em.merge(b2b);
        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}