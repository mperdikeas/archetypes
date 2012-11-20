package gr.neuropublic.persutil;

import mutil.base.Pair;

public class SelectComponent extends NonValueBearingJPQLComponent {
    private Class klass;
    private static final String alias = "t";
    public SelectComponent(Class klass) {this.klass = klass;}
    
    @Override
    public String queryCompNoParameter() {
        return "SELECT "+alias+" FROM "+klass.getSimpleName() +" "+alias;
    }

    public Class getKlass() {return this.klass;}
    public String getAlias() {return this.alias;}
}