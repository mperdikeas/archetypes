package gr.neuropublic.jsf.base;

import java.util.HashMap;
import java.util.Map;

public abstract class LovHelper<T> {
    
    public abstract void saveEnity(T ent);
    
    public Map getFilters() {
        return new HashMap<String, Object>();
    }
}
