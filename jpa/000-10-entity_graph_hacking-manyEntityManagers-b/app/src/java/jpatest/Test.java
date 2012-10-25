package jpatest;
import javax.persistence.*;
import entities.*;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;

class Statistics {
    private Map<Long, Integer> stats = new HashMap<Long, Integer>();
    public void addValue(long l) {
        if (stats.containsKey(l))
            stats.put(l, stats.get(l)+1);
        else
            stats.put(l, 1);
    }
    public Map<Long, Integer> getStats() { return stats; }
}
        

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

    private static EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        l.info("entity manager factory obtained");
        EntityManager em = entityManagerFactory.createEntityManager();
        l.info("entity manager obtained");
        return em;
    }

    private static EntityManager[] getEntityManagers(int howMany) {
        EntityManager retValue[] = new EntityManager[howMany];
        for (int i = 0 ; i < retValue.length ; i++)
            retValue[i] = getEntityManager();
        return retValue;
    }

    private static EntityManager randomEntityManager(EntityManager[] ems) {
        return ems[random.nextInt(ems.length)];
    }

    private static Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) throws InterruptedException {
        Statistics stats = new Statistics();       
        
        final int NUM_OF_ENTITY_MANAGERS = 3;
        EntityManager[] ems = getEntityManagers(NUM_OF_ENTITY_MANAGERS);
        int uniqueHashCode = 0;
        long millisFor1stFetch = 0;
        A a = null;
        {
            long t0 = System.currentTimeMillis();
            a = randomEntityManager(ems).find(A.class, 1);
            uniqueHashCode = System.identityHashCode(a);
            stats.addValue(uniqueHashCode);
            long t1 = System.currentTimeMillis();
            millisFor1stFetch = t1 - t0;
        }
        l.info("1st object created. Value was: "+a);

        final int ITER=1000;
        long fetchTimes[] = new long[ITER];
        A lastA = null;
        for (int i = 0 ; i < ITER ; i++) {
            long t0 = System.currentTimeMillis();
            lastA = randomEntityManager(ems).find(A.class, 1);
            long newHashCode = System.identityHashCode(lastA);
            // if (newHashCode != uniqueHashCode) throw new RuntimeException(newHashCode+"!="+uniqueHashCode); // this is always triggered with more than one entity manager
            stats.addValue(newHashCode);
            long t1 = System.currentTimeMillis();
            fetchTimes[i] = t1 - t0;
        }
        l.info("last object fetched was: "+lastA);
        l.info("all identity codes are the same: "+uniqueHashCode);
        l.info(A.instances+" A objects created");
        l.info("first fetch took: "+millisFor1stFetch+" ms");        
        l.info("subsequent "+ITER+" fetches average: "+Test.avg(fetchTimes)+" ms, total: "+Test.sum(fetchTimes)+" ms.");
        /*
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");
        */
        for (EntityManager em : ems) {
            em.close();
            l.info("entity manager closed");
            em.getEntityManagerFactory().close();
            l.info("entity manager factory closed");
        }
        Map<Long, Integer> results = stats.getStats();
        for (Long hashcode : results.keySet())
            l.info(hashcode+" --> "+results.get(hashcode));
    }
}