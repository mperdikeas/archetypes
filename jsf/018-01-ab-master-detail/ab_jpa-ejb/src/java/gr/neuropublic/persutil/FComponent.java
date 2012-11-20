package gr.neuropublic.persutil;

import mutil.base.Pair;

public class FComponent extends NonValueBearingJPQLComponent {
    private String alias;
    private String fieldName;
    public FComponent(String alias, String fieldName) {
        this.alias     = alias;
        this.fieldName = fieldName;
    }
    
    @Override
    public String queryCompNoParameter() {
        return " "+alias+"."+fieldName;
    }
}