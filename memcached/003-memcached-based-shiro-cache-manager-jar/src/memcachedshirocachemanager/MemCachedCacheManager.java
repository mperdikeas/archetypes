package memcachedshirocachemanager;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import net.spy.memcached.MemcachedClient;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;


public class MemCachedCacheManager implements CacheManager {

    private static MemcachedClient c ;
    private static int instanceCount = 0 ; 
    private Map<String, Cache> createdCaches = new HashMap<String, Cache>();

    private static MemCachedCacheManager theManager;

    public MemCachedCacheManager() throws IOException {
        if (++instanceCount != 1) throw new RuntimeException("was expecting this to be a singleton"); /* line A  */
        c = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        this.theManager = this;
    }
    // NB: with regard to the above constructor and the below static factory method
    // Actually, we need both: Shiro initialization expects a no-arg constructor
    // but in case my own code needs to use a MemCacheCacheManager for its own inscrutable
    // purposes we also need the factory method to enforce the singleton.
    public static MemCachedCacheManager createMemCachedCacheManager() throws IOException {
        if (theManager==null) {
            theManager = new MemCachedCacheManager();
        }
        return theManager;
    }

    public <K,V> Cache<K,V> getCache(String name) {
        if (createdCaches.get(name)==null) {
            System.out.println("cache created anew");
            Cache<K,V> newCache = new MemCachedCache(c, name);
            createdCaches.put(name, newCache);
        } else System.out.println("cache already existed");
        return createdCaches.get(name);
    }


}