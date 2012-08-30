import net.sf.ehcache.*;

public class HelloEhcache {
    public static void printCacheNames() {
        String[] cacheNames = CacheManager.getInstance().getCacheNames();
        System.out.println(String.format("%d caches found", cacheNames.length));
        for (String cacheName : cacheNames) {
            System.out.println("cache: "+cacheName);
        }
    }
    public static void main(String[] args) {
        System.out.println("foo");
        CacheManager.create();
        printCacheNames();
        CacheManager singletonManager = CacheManager.getInstance();
        singletonManager.addCache("cache1");
        singletonManager.addCache("cache2");
        printCacheNames();
        Cache cache1 = singletonManager.getCache("cache1");
        Cache cache2 = singletonManager.getCache("cache2");
        cache1.put(new Element("cache1key1", "cache1 - hello world"));
        cache2.put(new Element("cache2key1", "cache2 - hello world"));
        Element element1 = cache1.get("cache1key1");
        Element element2 = cache1.get("cache2key1");
        if (element2==null) {
            System.out.println("oops .. doesn't exist in cache1 - try cache2");
            element2 = cache2.get("cache2key1");
        }
        System.out.println((String) element1.getObjectValue());
        System.out.println((String) element2.getObjectValue());
    }
}