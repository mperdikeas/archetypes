package jpatest;
import javax.persistence.*;
import javax.persistence.metamodel.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
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

    private static void printEntities(EntityManager em) {
        for (EntityType<?> entity : em.getMetamodel().getEntities()) {
            final String className = entity.getName();
            l.info("PRINTING all "+ className+ " entities");
            Query q = em.createQuery("from " + className + " c");
            List objects = q.getResultList();
            for (Object o : objects)
                l.info("object: "+o);
            l.info("*************** ok: " + className);
        }
    }


    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        pauseForKeybdInput("about to print all entities before getTransaction()")    ; printEntities(em);
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        pauseForKeybdInput("about to print all entities after transaction has begun"); printEntities(em);
        pauseForKeybdInput("About to find a B object and print number of instance before and after");
        l.info("B # of instances before find = "+B.numOfInstances());
        B b = em.find(B.class, 6); // This value does not initially exist in the database (which only contains 6
                                   // objects with ids in the [0,5] range) - so you have to add it manually 
                                   // and see the new object counter being incremented. If you change the code
                                   // to search for a value in the [0,5] range you see that no new B() is created.
        l.info("B # of instances after find  = "+B.numOfInstances());
        pauseForKeybdInput("about to print all entities after B has been found")     ; printEntities(em);        
        pauseForKeybdInput("About to detach B object");
        em.detach(b);
        pauseForKeybdInput("About to commit");
        entityTransaction.commit();
        em.close();
        entityManagerFactory.close();
        l.info("A # of instances = "+A.numOfInstances());
        l.info("B # of instances = "+B.numOfInstances());
    }
}