package translation;

import java.util.Map;
import java.util.HashMap;

public class TranslationDatabase {
  private static final Map<String, String> wordMap = new HashMap<String, String>();

  static {
    wordMap.put("red"  , "κόκκινο");
    wordMap.put("blue" , "μπλέ");
    wordMap.put("green", "πράσινο");
    wordMap.put("white", "λευκό");
    wordMap.put("black", "μαύρο");
    wordMap.put("lightGray", "ελαφρύ γκρίζο");
    wordMap.put("gray", "γκρίζο");
    wordMap.put("darkGray", "βαθύ γκρίζο");
    wordMap.put("pink", "ροζ");
    wordMap.put("orange", "πορτοκαλί");
    wordMap.put("yellow", "κίτρινο");
    wordMap.put("cyan", "κυανό");
  }


  // Simulates retrieving expensive object from SOR.
  public String getTranslation(String word) {
    String translatedWord = wordMap.get(word);
    if(translatedWord == null) {
      return null;
    }
    try {
      Thread.sleep(3000);
    } catch(Exception e) {}
    return translatedWord;
  }
}
