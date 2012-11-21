package gr.neuropublic.persutil;

import java.util.Map;
import mutil.base.Pair;

public class Ready {
    private JPQLBuilder JPQLBuilder;
    public Ready(JPQLBuilder JPQLBuilder) {
        this.JPQLBuilder = JPQLBuilder;
    }
    public Pair<String, Map<String, Object>> getQuery() {
        return JPQLBuilder.getQuery();
    }
}