import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import org.apache.shiro.cache.CacheManager;


public class MemCachedCacheManager implements CacheManager {

    private static MemcachedClient c ;

    public MemCachedCacheManager() {
        c = new MemcachedClient(new InetSocketAddress("localhost", 11211));        
    }

    public <K,V> Cache<K,V> getCache(String name) {
        throw new UnsupportedOperationException();
    }


}