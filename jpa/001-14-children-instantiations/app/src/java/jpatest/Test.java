package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.*;

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

    private static void pauseForKeybdInput(String msg) throws Exception {
        l.info(msg+" - hit keyboard to proceed");
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }


    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        pauseForKeybdInput("About to finding B object");
        B b = em.find(B.class, 1);
        l.info("a is attached ? "+em.contains(b.getAId()));
        pauseForKeybdInput("About to detach B object");
        em.detach(b);
        l.info("a is attached ? "+em.contains(b.getAId()));
        if (true) // line-46
            if (em.contains(b.getAId())) { // line-47
                l.info("before the collection removal a was: "+b.getAId());
                b.getAId().internalRemoveB(b);
                l.info("after the collection removal a is: "  +b.getAId());
            }
        pauseForKeybdInput("About to commit");
        entityTransaction.commit(); // Depending on whether we have the CascadeType.DETACH or not (default is not) in B.java:line-42 this may fail. It only 
                                    // succeeds if DETACH is on for the many-to-one relationship from B to A. However this is not the regular practice.
                                    // If DETACH is off, then depending on the switch of Test.java:line-46 the failure is either "detached entity passed to persist"
                                    // (if the switch is 'false') or "Removing a detached instance" (if the switch is 'true'). If DETACH is on then the 
                                    // value of the switch doesn't come into play (cause the rest of the logic is not executed due to the guard in line-47) and
                                    // the code succeeds.
        em.close();
        entityManagerFactory.close();
        l.info("A # of instances = "+A.numOfInstances());
        l.info("B # of instances = "+B.numOfInstances());
    }
}