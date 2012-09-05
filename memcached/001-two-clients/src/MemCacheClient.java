import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.io.Console;

public class MemCacheClient {
    private static MemcachedClient c ;
    public static void main(String[] args) throws Exception {
        c = new MemcachedClient(new InetSocketAddress("localhost", 11211));
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
        // Store a value (async) for one hour
        c.set(key, 3600, value);
        System.out.println("stored the key");
    }

    private static void get(String key) {
        // Retrieve a value (synchronously).
        String message = (String) c.get(key);
        System.out.println("retrieved the value of: "+message);
    }
}