import net.sf.ehcache.*;

public class HelloEhcache {
    public static void main(String[] args) {
        System.out.println("foo");
        CacheManager.create();
    }
}