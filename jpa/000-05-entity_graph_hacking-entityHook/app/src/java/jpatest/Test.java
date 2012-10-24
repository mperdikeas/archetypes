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

        Set<B> bsToRemove = new LinkedHashSet<B>();

        while (bsI.hasNext()) {
            B b = bsI.next();
            if (b.getId()%2==0)
                bsToRemove.add(b);
        }

        for (Iterator<B> bsToRemoveI = bsToRemove.iterator(); bsToRemoveI.hasNext();) {
            B bToRemove = bsToRemoveI.next();
            em.remove(bToRemove);
            // bToRemove.setAId(null); we no longer need this line because of the hook in line 54 of B.java
        }
        entityTransaction.begin();  // see http://stackoverflow.com/questions/13047919/jpa-standalone-mode-changing-managed-entity-fields-outside-entitytransaction-w
        entityTransaction.commit(); // now the change in line 31 finds its way to the database.
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}