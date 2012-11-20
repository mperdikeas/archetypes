package gr.neuropublic.persutil;

public class Select {
    
    public static Where from(Class klass) {
        JPQLBuilder jp = new JPQLBuilder();
        jp.push(new SelectComponent(klass));
        return new Where(jp);
    }

}