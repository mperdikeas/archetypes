import java.net.InetSocketAddress;
import java.io.Console;
import java.util.Set;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;

public class ShiroMemCacheConsoleClient {
    private static Cache c ;
    public static void main(String[] args) throws Exception {
        c = (new MemCachedCacheManager()).getCache("foo"); // I should later implement an ability of the console program to 'connect' to another database
        final Console console = System.console();
        while (true) {
            String line = console.readLine();
            String[] command = line.split(" ");
            switch (command[0]) {
            case "put"    : put(command[1], command[2]);
                            break;
            case "get"    : get(command[1]);
                            break;
            case "remove" : remove(command[1]);
                            break;
            case "clear"  : clear();
                            break;
            case "keys"   : keys();
                            break;
            default:
                throw new RuntimeException();
            }
        }
    }

    private static void put(String key, String value) {
        String oldValue = (String) c.put(key, value);
        System.out.println("old value was: "+oldValue);
    }

    private static void get(String key) {
        System.out.println("value is: "+c.get(key));
    }

    private static void remove(String key) {
        c.remove(key);
    }

    private static void clear() {
        c.clear();
    }

    private static void keys() {
        Set<String> keys = (Set<String>) c.keys();
        if (keys!=null) {
            for (String key : keys) 
                System.out.print(key+" ");
            System.out.println();
        } else System.out.println("keyset is null");
    }
}