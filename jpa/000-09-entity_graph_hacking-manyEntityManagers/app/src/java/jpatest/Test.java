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
    private static long avg (long tbl[]) {
        long sum = 0;
        for (int i = 0 ; i < tbl.length ; i++)
            sum += tbl[i];
        return sum/tbl.length;
    }

    private static long sum (long tbl[]) {
        long sum = 0;
        for (int i = 0 ; i < tbl.length ; i++)
            sum+=tbl[i];
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        EntityTransaction entityTransaction = em.getTransaction();

        int uniqueHashCode = 0;
        long millisFor1stFetch = 0;
        {
            long t0 = System.currentTimeMillis();
            A a = em.find(A.class, 1);
            uniqueHashCode = System.identityHashCode(a);
            long t1 = System.currentTimeMillis();
            millisFor1stFetch = t1 - t0;
        }
        final int ITER=1000;
        long fetchTimes[] = new long[ITER];
        for (int i = 0 ; i < ITER ; i++) {
            long t0 = System.currentTimeMillis();
            A a = em.find(A.class, 1);
            if (System.identityHashCode(a) != uniqueHashCode) throw new RuntimeException();
            long t1 = System.currentTimeMillis();
            fetchTimes[i] = t1 - t0;
        }          
        l.info("all identity codes are the same: "+uniqueHashCode);
        l.info(A.instances+" A objects created");
        l.info("first fetch took: "+millisFor1stFetch+" ms");        
        l.info("subsequent "+ITER+" fetches average: "+Test.avg(fetchTimes)+" ms, total: "+Test.sum(fetchTimes)+" ms.");
        /*
        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");
        */
        em.close();
        l.info("entity manager closed");
        entityManagerFactory.close();
        l.info("entity manager factory closed");
    }
}