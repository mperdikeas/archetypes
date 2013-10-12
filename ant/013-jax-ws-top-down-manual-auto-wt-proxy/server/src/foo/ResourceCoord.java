package foo;

import java.util.Map;
import java.util.HashMap;

import mutil.base.Pair;


public class ResourceCoord {

    private String filePath;
    private Map<String, Pair<Boolean, String>> regExpSubs;

    public String getFilePath() {
        return this.filePath;
    }

    public Map<String, Pair<Boolean, String>> getRegExpSubs() {
        return this.regExpSubs;
    }

    public ResourceCoord(String filePath, Map<String, Pair<Boolean, String>> regExpSubs) {
        this.filePath = filePath;
        this.regExpSubs = regExpSubs;
    }

    public static ResourceCoord create(String filePath) {
        Map<String, Pair<Boolean, String>> regExpSubs = new HashMap<>();
        return new ResourceCoord(filePath, regExpSubs);
    } 

    public static ResourceCoord create(String filePath, String regExp, boolean all, String replacement) {
        Map<String, Pair<Boolean, String>> regExpSubs = new HashMap<>();
        regExpSubs.put(regExp, Pair.create(all, replacement));
        return new ResourceCoord(filePath, regExpSubs);
    } 

}
