import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.URL;

public class TranslationCache {

    private static final TranslationDatabase translationDatabase = new TranslationDatabase();

    private String ehcacheConfigFileName;
    public TranslationCache(String ehcacheConfigFileName) {
        this.ehcacheConfigFileName = ehcacheConfigFileName;
    }

    private static CacheManager cacheManager = null;
    private CacheManager getCacheManager() {
        if (cacheManager==null) {
            URL url = this.getClass().getResource("/"+ehcacheConfigFileName);
            System.out.println("URL calculated as: "+url);
            cacheManager = CacheManager.create(url);
        }
        return cacheManager;
    }

  public String getTranslation(String word) {
    Element elem = getCache().get(word);
    if (elem == null) {
      String translatedWord = translationDatabase.getTranslation(word);
      if (translatedWord == null) { return null; }
      getCache().put(elem = new Element(word, translatedWord));
    }
    return (String) elem.getValue();
  }

  private String getCachedTranslation(String name) {
    Element elem = getCache().get(name);
    return elem != null ? (String) elem.getValue() : null;
  }

  public String[] getOriginalWordsInCache() {
    @SuppressWarnings("unchecked")
    Iterator<String> keys = ((List<String>) getCache().getKeys()).iterator();
    List<String> list = new ArrayList<String>();
    while (keys.hasNext()) {
      String name = keys.next();
      if (getCachedTranslation(name) != null) {
        list.add(name);
      } else throw new RuntimeException("very rare - wasn't expecting that - race condition?");
    }
    return list.toArray(new String[list.size()]);
  }

  public long getTTL() {
    return getCache().getCacheConfiguration().getTimeToLiveSeconds();
  }

  public long getTTI() {
    return getCache().getCacheConfiguration().getTimeToIdleSeconds();
  }

  public int getSize() {
    return getCache().getSize();
  }

  private Ehcache getCache() {
      final String cacheName = "dictionary";
      Ehcache retValue = getCacheManager().getEhcache(cacheName);
      if (retValue == null) throw new RuntimeException("unable to find cache: "+ cacheName);
      return retValue;
  }
}
