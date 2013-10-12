package foo;

import java.util.Map;
import mutil.base.Pair;

public class ResourceMap {

    private Map < Pair<String, String>, ResourceCoord> map;

    public ResourceMap(Map < Pair<String, String>, ResourceCoord> map) {
        this.map = map;
    }

    public ResourceCoord get(String requestURI, String queryString) {
        return map.get(Pair.create(requestURI, queryString));
    }

}