package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class Test {
    private static final Logger l = Logger.getLogger(Test.class.getName());
    public static void main(String[] args) throws InterruptedException {
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

        int i = 0;
        for (Iterator<B> bsToRemoveI = bsToRemove.iterator(); bsToRemoveI.hasNext();) {
            B bToRemove = bsToRemoveI.next();
            if (i++ % 2 ==0) {
                l.info("removing through Entity Manager");
                em.remove(bToRemove); // line 51
            } else {
                l.info("removing through collection remove");
                // bToRemove.setAId(null); we no longer need this line because of the hook in line 54 of B.java
                a.removeB(bToRemove); // this alone, doesn't work without the DELETE_ORPHAN // line 55
            }
        }
        B b20 = new B(20, "new value for 20");  // if key 2 is used instead I get the "a different object with the same identifier value was already associated with the session" exception
        a.addB(b20);
        B b2 = em.find(B.class, 2); // just finding the object shouldn't create any problems
        em.persist(b2);
        em.merge(b2); // hell yeah you can even persist and merge it if you like, it won't go to the database.
        b2.setId(22); // but if you change the id and try to merge it...
        em.merge(b2); // curiously no B # 22 is created ..., not even if you do it inside a transaction (line 73)
        B b4 = new B(4, "new value for 4");
        a.addB(b4);
        final int SLEEP_SECS = 20;
        l.info("sleeping for "+SLEEP_SECS+" seconds");
        Thread.sleep(SLEEP_SECS*1000);
        l.info("about to begin the transaction"); // I 've verified that both kinds of removals (line 51 and line 55) become
                                                  // visible after the commit in line 64.
        entityTransaction.begin();  // see http://stackoverflow.com/questions/13047919/jpa-standalone-mode-changing-managed-entity-fields-outside-entitytransaction-w
        em.merge(b2); // line 73
        entityTransaction.commit(); // now the change in line 31 finds its way to the database. // line 64
        l.info("just commited the transaction");
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}