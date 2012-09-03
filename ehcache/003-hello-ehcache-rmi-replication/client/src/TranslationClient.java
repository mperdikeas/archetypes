import java.io.Console;
import java.io.IOException;
public class TranslationClient {
    public static void main(String[] args) {
        String ehcacheConfigFileName = args[0];
        System.out.println("EhCache config name read as: "+ehcacheConfigFileName);
        TranslationCache tc = new TranslationCache(ehcacheConfigFileName);
        Console c = System.console();
        while (true) {
            System.out.println("word to translate: ");
            String word = c.readLine();
            if (tc.getTranslation(word) == null) {
                System.out.println(String.format("word %s is unknown in cache, add translation:", word));
                String translation = c.readLine();
                tc.putTranslation(word, translation);
                System.out.println(String.format("translation %s --> %s added in cache", word, translation));
            } else
                System.out.println("translation is: "+tc.getTranslation(word));
        }
    }
}