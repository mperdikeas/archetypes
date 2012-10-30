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

        A a = em.find(A.class, 1);
        B b = getARandomChildOfA(a);

        // combinations  -- line-37
        //  true, true  --> working        (this is strange, because in the web scenario this combination failed)
        //  true, false --> working   
        // false, true  --> not working    (quiet failure)
        // false, false --> working        (this only works if the orphanRemoval property in entity
        //                                  ... A is set to true, blows up with SQL constraint
                                                violation if said property is set to false)


        boolean cutFromA             = false;
        boolean entityManagerRemoval = false;

             if ( cutFromA &&  entityManagerRemoval ) {b.preRemoveCutFromA();  em.remove(b);}
        else if (!cutFromA &&  entityManagerRemoval ) {                        em.remove(b);}
        else if ( cutFromA && !entityManagerRemoval ) {b.preRemoveCutFromA();               }
        else if (!cutFromA && !entityManagerRemoval ) {b.setAId(null);                      }
        else throw new RuntimeException("bug");


     
        entityTransaction.begin();
        entityTransaction.commit();
        l.info("just commited the transaction");


        em.close();
        entityManagerFactory.close();
    }
}