package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class Test {
    private static final Logger l = Logger.getLogger(Test.class.getName());
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        EntityTransaction entityTransaction = em.getTransaction();
        final boolean INSERT_2ND_VALUE = false;
        if (INSERT_2ND_VALUE) {
            l.info("entity transaction obtained");
            entityTransaction.begin();
            l.info("transaction begun");
            A a = new A(2, "a second record");
            em.persist(a);
            l.info("object persisted");
            entityTransaction.commit();
            l.info("transaction commited");
        }

        A a = em.find(A.class, 1);
        String newValue = "G"+a.getA1();
        a.setA1( newValue.substring(0, newValue.length()>50?50:newValue.length()) ); // line 31
        // em.persist(a);    // the entity doesn't have to be persisted or merged, it's already managed since we
        // em.merge(a);      // obtained it via an em.find
        l.info("number of children is: "+a.getBCollection().size());
        Set<B> bs = a.getBCollection();
        Iterator<B> bsI = bs.iterator();
        entityTransaction.begin();  // strangly that this also works
        Set<B> bsToRemove = new LinkedHashSet<B>();

        // true, true works
        boolean directRemoval     = true;
        boolean collectionRemoval = true;

        while (bsI.hasNext()) {
            B b = bsI.next();
            if (directRemoval) {
                // b.setAId(null);
                em.remove(b);
            }
            if (collectionRemoval) bsToRemove.add(b);
        }
        // em.detach(a);        // if this is uncommented the changes to A in line 31 do not reach the DB
        if (collectionRemoval) {
            Iterator<B> bsToRemoveI = bsToRemove.iterator();
            while (bsToRemoveI.hasNext()) {
                B b = bsToRemoveI.next();
                boolean removed = bs.remove(b);
                l.info((removed?"removed ":"failed to remove")+b+" from A's collection");
            }
        }
        
        entityTransaction.commit();
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}