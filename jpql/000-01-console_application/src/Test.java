import mutil.jpapersutil.JPQLUtil;
import mutil.jpapersutil.QualifiedResultList;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import mutil.base.Pair;

import java.util.Map;
import java.util.HashMap;


public class Test {
    
    public static void main(String[] args) {
        System.out.println("foo");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("league");
        EntityManager em = factory.createEntityManager();

        //     public static <T> QualifiedResultList<T> getResults(EntityManager em, Pair<String, Map<String, Object>> jpql_and_params, Integer recordLimit) {

        //       String query1 = "SELECT c FROM gr.neuropublic.neurojsfpilot.customerservice.entities.Customer c";
         String query1 = "SELECT c FROM Customer c";
        String query2 = "SELECT c FROM Customer c WHERE c.id = :id";
        String query3 = "SELECT c FROM Customer c WHERE c.name = :name";
        String query4 = "SELECT c FROM Customer c WHERE c.surname = :surname";
        String query5 = "SELECT c FROM Customer c WHERE c.employee = :employee";
        String query6 = "SELECT c FROM Customer c WHERE c.dateOfBirth = :dateOfBirth";
        String query7 = "SELECT c FROM Customer c WHERE c.streetNo = :streetNo";
        Pair<String, Map<String, Object>> jpql_and_params = Pair.create(query1, (Map<String, Object>) new HashMap<String,Object>());
        QualifiedResultList<Object> result = JPQLUtil.getResults(em , jpql_and_params , 10);
        System.out.println(String.format("%d results returned%s, these being:\n", result.data.size(), (result.theresMore?" (there's more)":"")));
        for (Object object : result.data)
            System.out.println("result: "+object.toString());
    }
}