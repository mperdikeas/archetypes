import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import javax.ws.rs.core.UriBuilder;

public class JAXRSClient {


    public static void main(String[] args) throws Exception {
        {
            ClientRequest cr = new ClientRequest("http://mp.gaia.gr:8080/helloJersey/rest/actions/say/hi/to/me");
            String result = cr.get(String.class).getEntity();
            System.out.println(result);
        }
        {
            ClientRequest cr = new ClientRequest("http://mp.gaia.gr:8080/helloJersey/rest/actions?a=the%20value%20of%20alpha&b=the-value-of-beta");
            String result = cr.get(String.class).getEntity();
            System.out.println(result);
        }
        {   // more idiomatic way:
            ClientRequest cr = new ClientRequest("http://mp.gaia.gr:8080/helloJersey/rest/actions");
            cr.queryParameter("a", "the value of alpha");
            cr.queryParameter("b", "the value of beta");
            String result = cr.get(String.class).getEntity();
            System.out.println(result);
        }
        {   // even more idiomatic way:
            ClientRequestFactory crf = new ClientRequestFactory(UriBuilder.fromUri("http://mp.gaia.gr:8080/helloJersey").build());
            ClientRequest cr = crf.createRelativeRequest("/rest/actions");
            cr.queryParameter("a", "the value of alpha in the most idiomatic way possible");
            cr.queryParameter("b", "the value of beta in the most idiomatic way possible");
            String result = cr.get(String.class).getEntity();
            System.out.println(result);
        }
    }
}
