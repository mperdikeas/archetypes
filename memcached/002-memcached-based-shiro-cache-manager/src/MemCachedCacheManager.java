import java.net.InetSocketAddress;
import java.io.IOException;

import net.spy.memcached.MemcachedClient;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;


public class MemCachedCacheManager implements CacheManager {

    private static MemcachedClient c ;

    public MemCachedCacheManager() throws IOException {
        c = new MemcachedClient(new InetSocketAddress("localhost", 11211));        
    }

    public <K,V> Cache<K,V> getCache(String name) {
        throw new UnsupportedOperationException();
    }


}