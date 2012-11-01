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
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

        A a = em.find(A.class, 1);
        B b = em.find(B.class, 2);
        a.getBCollection().remove(b);
        pauseForKeybdInput("a");
        em.detach(a);
        pauseForKeybdInput("b");
        em.merge(a);
        pauseForKeybdInput("c");
        entityTransaction.commit();
        pauseForKeybdInput("d");
        em.close();
        entityManagerFactory.close();

    }
}