
import java.net.InetSocketAddress;
import java.io.Console;
import org.apache.shiro.cache.CacheManager;

public class MemCacheClient {
    private static CacheManager c ;
    public static void main(String[] args) throws Exception {
        c = new MemCachedCacheManager();
        final Console console = System.console();
        while (true) {
            String line = console.readLine();
            String[] command = line.split(" ");
            switch (command[0]) {
            case "put" : put(command[1], command[2]);
                         break;
            case "get" : get(command[1]);
                         break;
            default:
                throw new RuntimeException();
            }
        }
    }

    private static void put(String key, String value) {
        throw new UnsupportedOperationException();
        // Store a value (async) for one hour
        // c.set(key, 3600, value);
        // System.out.println("stored the key");
    }

    private static void get(String key) {
        throw new UnsupportedOperationException();
        // Retrieve a value (synchronously).
        // String message = (String) c.get(key);
        // System.out.println("retrieved the value of: "+message);
    }
}