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


    public static void main(String[] args) throws Exception {
        for (int i = 0 ; i < args.length ; i++) {
            if ((!args[i].equals("true")) && (!args[i].equals("false"))) throw new RuntimeException("unrecognized argument: "+args[i]);
            l.info(String.format("arg: %d -> %s", i, args[i]));        
        }
        if (args.length !=2) throw new RuntimeException("expecting two arguments: 'true/false' 'true/false' arguments");
        boolean detachA = Boolean.parseBoolean(args[0]);
        boolean detachB = Boolean.parseBoolean(args[1]);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("testjpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        l.info("hit keyboard to create A object");
        (new BufferedReader(new InputStreamReader(System.in))).readLine();        
        A a = em.find(A.class, 1);
        l.info("A object created");
        B b = findInA(a, 1);
        l.info("b is managed before commit ? "+em.contains(b));
        entityTransaction.commit();
        l.info("b is managed after commit ? "+em.contains(b));
        l.info("**************** STEP 1 : detaching a and b");
        if (detachA) { l.info("detaching a"); em.detach(a); }
        if (detachB) { l.info("detaching b"); em.detach(b); }

        l.info("hit a key when've modified the B table in the database");
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
        l.info("**************** STEP 2 : merging a again");
        em.merge(b);
        l.info("merge finished. Hit a key to conclude.");
        (new BufferedReader(new InputStreamReader(System.in))).readLine();
        em.close();
        entityManagerFactory.close();
        l.info("A # of instances = "+A.numOfInstances());
        l.info("B # of instances = "+B.numOfInstances());
    }
}