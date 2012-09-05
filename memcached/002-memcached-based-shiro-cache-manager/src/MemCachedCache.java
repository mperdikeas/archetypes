import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import org.apache.shiro.cache.Cache;
import java.util.Set;
import java.util.Collection;

public class MemCachedCache<K,V> implements Cache<K,V> {

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    public Set<K> keys() {
        throw new UnsupportedOperationException();
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

}