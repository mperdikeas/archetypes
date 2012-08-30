import java.io.Console;
import java.io.IOException;
public class TranslationClient {
    public static void main(String[] args) {
        TranslationCache tc = new TranslationCache();
        Console c = System.console();
        while (true) {
            System.out.println("word to translate: ");
            String word = c.readLine();
            System.out.println("translation is: "+tc.getTranslation(word));
        }
    }
}