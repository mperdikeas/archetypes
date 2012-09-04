import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.URL;

public class EhCacheFacade {

    private String ehcacheConfigFileName;
    public EhCacheFacade(String ehcacheConfigFileName) {
        this.ehcacheConfigFileName = ehcacheConfigFileName;
    }

    private static CacheManager cacheManager = null;
    private CacheManager getCacheManager() {
        if (cacheManager==null) {
            URL url = this.getClass().getResource("/"+ehcacheConfigFileName);
            if (url==null)
                throw new RuntimeException();
            else {
                System.out.println("URL calculated as: "+url);
                cacheManager = CacheManager.create(url);
            }
        }
        return cacheManager;
    }

  private String getValueAsString(String name) {
    Element elem = getCache().get(name);
    if (elem != null) {
        Object key   = elem.getObjectKey();
        Object value = elem.getObjectValue();
        System.out.println("key class is: "+key.getClass());
        System.out.println("value class is: "+value.getClass());
    }
    return elem != null ? elem.toString() : "null";
  }

  public String[] getCacheContents() {
    @SuppressWarnings("unchecked")
    Iterator<String> keys = ((List<String>) getCache().getKeys()).iterator();
    List<String> list = new ArrayList<String>();
    while (keys.hasNext()) {
      String name = keys.next();
      if (getValueAsString(name) != null) {
          list.add(String.format("%s --> %s", name, getValueAsString(name)));
      } else throw new RuntimeException("very rare - wasn't expecting that - race condition?");
    }
    return list.toArray(new String[list.size()]);
  }

  public int getSize() {
    return getCache().getSize();
  }

  private Ehcache getCache() {
      final String cacheName = "active-sessions-foo";
      Ehcache retValue = getCacheManager().getEhcache(cacheName);
      if (retValue == null) throw new RuntimeException("unable to find cache: "+ cacheName);
      return retValue;
  }
}
