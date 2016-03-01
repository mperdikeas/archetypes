import mutil.base.JsonProvider;
import mutil.base.Pair;

import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;





public class FooMain {

    public static List<Pair<String, Type>> jsonFilesAndTypes;
    static {
        jsonFilesAndTypes = new ArrayList<>();
        jsonFilesAndTypes.add( new Pair<String, Type>("json1.json", new TypeToken<Map<String, Integer>>() {}.getType()) );
        jsonFilesAndTypes.add( new Pair<String, Type>("json2.json", new TypeToken<Map<String, Map<String, Integer>>>() {}.getType()));
    }

    public static void main(String args[]) throws IOException {

        System.out.printf("Note that it's not strictly possible to have a composite object as key in JSON - see:\n\n\t\t%s\n\n"
                          , "http://stackoverflow.com/a/11634031/274677");

        for (Pair<String, Type> jsonFileAndType: jsonFilesAndTypes) {
            String json = Files.toString(new File(jsonFileAndType.a), StandardCharsets.UTF_8);
            Map<String, Integer> m = JsonProvider.fromJson(json, jsonFileAndType.b);
        }
    }
}
