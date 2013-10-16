package foo;

import java.util.Map;
import java.util.HashMap;

import mutil.base.Pair;

public class ResourceMapBuilder {


    private Map < Pair<String, String>, ResourceCoord> map;
    private boolean spat ;
    public ResourceMapBuilder() {
        this.map = new HashMap<>();
        this.spat = false;
    }

    public ResourceMap spit() {
        this.spat = true;
        return new ResourceMap(this.map);
    }

    public ResourceMapBuilder addConfig(String requestURI, String queryString, String filePath) {
        if (this.spat)
            throw new IllegalStateException();
        else {
            map.put(Pair.create(requestURI, queryString), ResourceCoord.create(filePath));
            return this;
        }
    }

    public ResourceMapBuilder addConfig(String requestURI, String queryString, String filePath, String regExp, boolean all, String replacement) {
        if (this.spat)
            throw new IllegalStateException();
        else {
            map.put(Pair.create(requestURI, queryString), ResourceCoord.create(filePath, regExp, all, replacement));
            return this;
        }
    }

}