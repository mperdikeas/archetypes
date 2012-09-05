import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;

public class MemCacheHello {
    public static void main(String[] args) throws Exception {
        MemcachedClient c=new MemcachedClient(new InetSocketAddress("localhost", 11211));
        // Store a value (async) for one hour
        c.set("someKey", 3600, new String("hello"));
        // Retrieve a value (synchronously).
        String message = (String) c.get("someKey");
        System.out.println("retrived cached message: "+message);
    }
}