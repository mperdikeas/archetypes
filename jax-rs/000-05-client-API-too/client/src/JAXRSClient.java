import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JAXRSClient {


    public static void main(String[] args) throws Exception {
        {
            URL myapp = new URL("http://mp.gaia.gr:8080/helloJersey/rest/actions/say/hi/to/me");
            URLConnection webconn = myapp.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(webconn.getInputStream()));
            String line;
            while ( (line = in.readLine()) != null)
                System.out.println(line);
            in.close();
        }
        {
            URL myapp = new URL("http://mp.gaia.gr:8080/helloJersey/rest/actions?a=the%20value%20of%20alpha&b=the-value-of-beta");
            URLConnection webconn = myapp.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(webconn.getInputStream()));
            String line;
            while ( (line = in.readLine()) != null)
                System.out.println(line);
            in.close();
        }
    }
}
