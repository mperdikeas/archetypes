import java.net.InetSocketAddress;
import java.io.Console;
import java.util.Set;
import java.util.Collection;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;

public class ShiroMemCacheConsoleClient {
    private static MemCachedCacheManager cacheManager ;
    private static Cache c ;

    private static final void printVerbs() {
        System.out.println("verbs are: use, put, get, remove, clear, keys, size, values");
    }

    public static void main(String[] args) throws Exception {
        cacheManager = new MemCachedCacheManager();
        final Console console = System.console();
        printVerbs();
        while (true) {
            String line = console.readLine();
            String[] command = line.split(" ");
            switch (command[0]) {
            case "use"    : use(command[1]);
                            break;
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
            case "size"   : size();
                            break;
            case "values" : values();
                            break;
            default:
                printVerbs();
            }
        }
    }

    private static void use(String cacheName) {
        c = cacheManager.getCache(cacheName);
    }

    private static boolean check() {
        if (c==null) {
            System.out.println("cache not set - use 'use' to set it");
            return false;
        } else return true;
    }

    private static void put(String key, String value) {
        if (!check()) return;
        String oldValue = (String) c.put(key, value);
        System.out.println("old value was: "+oldValue);
    }

    private static void get(String key) {
        if (!check()) return;
        System.out.println("value is: "+c.get(key));
    }

    private static void remove(String key) {
        if (!check()) return;
        c.remove(key);
    }

    private static void clear() {
        if (!check()) return;
        c.clear();
    }

    private static void keys() {
        if (!check()) return;
        Set<String> keys = (Set<String>) c.keys();
        if (keys!=null) {
            for (String key : keys) 
                System.out.print(key+" ");
            System.out.println();
        } else System.out.println("keyset is null");
    }
    
    private static void size() {
        if (!check()) return;
        c.size();
    }

    private static void values() {
        if (!check()) return;
        Collection<String> values = (Collection<String>) c.values();
        for (String value : values)
                System.out.print(value+" ");
        System.out.println();
    }
}