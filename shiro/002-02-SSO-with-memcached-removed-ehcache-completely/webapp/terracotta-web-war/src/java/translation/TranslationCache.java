package translation;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.Cache;
import memcachedshirocachemanager.MemCachedCacheManager;

public class TranslationCache {

    private static final Logger l = Logger.getLogger(TranslationCache.class.getName());

    private static TranslationCache tc = null;
    public static TranslationCache getTranslationCache() {
        l.info(" I am here (TranslationCache#getTranslationCache())" );
        if (tc == null) {
            l.info("TranslationCache#getTranslationCache(): tc is null, setting it");
            tc = new TranslationCache();
        } else l.info("TranslationCache#TranslationCache(): tc is not null");
        return tc;
    }

    private static final TranslationDatabase translationDatabase = new TranslationDatabase();

    private static CacheManager cacheManager;
    private CacheManager getCacheManager() {
        try {
            if (cacheManager==null) {
                cacheManager = new MemCachedCacheManager();
            }
            return cacheManager;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

  public String getTranslation(String word) {
    Object elem = getCache().get(word);
    if (elem == null) {
      String translatedWord = translationDatabase.getTranslation(word);
      if (translatedWord == null) { return null; }
      getCache().put(word, translatedWord);
    }
    return (String) elem;
  }

    public void putTranslation(String word, String translation) {
        getCache().put(word, translation);
        l.info(String.format("just put a new element (%s,%s), into the cache", word, translation));
    }

  private String getCachedTranslation(String name) {
    Object elem = getCache().get(name);
    return (elem != null) ? (String) elem : null;
  }

    public int getSizeOfTranslationDictionary() {
        return getOriginalWordsInCache().length;
    }

  public String[] getOriginalWordsInCache() {
    @SuppressWarnings("unchecked")
    Iterator<String> keys = ((List<String>) getCache().keys()).iterator();
    List<String> list = new ArrayList<String>();
    while (keys.hasNext()) {
      String name = keys.next();
      l.info(String.format("checking for the translation of word: '%s'", name));
      
      boolean showWeirdCondition = false;
      if (showWeirdCondition) {
          if (getCachedTranslation(name) != null) {
            list.add(name);
          } else throw new RuntimeException("very rare - wasn't expecting that - race condition?");
                  // this is really happening with my terracotta installation
                  // how is it possible that a key is found in the cache without the value ?
                  // I should investigate into it.
      }
      else list.add(name);
    }
    return list.toArray(new String[list.size()]);
  }



  public int getSize() {
      return getCache().size();
  }

  private Cache getCache() {
      final String cacheName = "dictionary";
      Cache retValue = getCacheManager().getCache(cacheName);
      if (retValue == null) throw new RuntimeException("unable to find cache: "+ cacheName);
      return retValue;
  }
}
