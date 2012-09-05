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

    public MemCachedCacheManager() throws IOException {
        if (++instanceCount != 1) throw new RuntimeException("was expecting this to be a singleton");
        c = new MemcachedClient(new InetSocketAddress("localhost", 11211));        
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