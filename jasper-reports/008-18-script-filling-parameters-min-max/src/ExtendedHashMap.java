import java.util.HashMap;
import java.util.Map;


public class ExtendedHashMap extends HashMap<Integer, Object> {

    public Map<String, Object> internal = new HashMap<String, Object>();
    public Object f(String key) {
        return internal.get(key);
    }

    public Object put(Integer key, String keys, Object value) {
        Object retValue = super.put(key, value);
        internal.put(keys, value);
        return retValue;
    }

    public static void main (String args[]) {
        System.out.println("boo");
        ExtendedHashMap fm = new ExtendedHashMap();
        fm.put(1, "one", "The integer known as 'one'");
        System.out.println(fm.get(1));
        System.out.println(fm.f("one"));
    }
}