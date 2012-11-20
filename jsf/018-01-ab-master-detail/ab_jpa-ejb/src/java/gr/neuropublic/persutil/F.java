package gr.neuropublic.persutil;

import java.util.StringTokenizer;

public class F {
    private JPQLBuilder jp;
    public F(JPQLBuilder jp) {
        this.jp = jp;
    }
    public FAfter f(String fieldName) {
        StringTokenizer st = new StringTokenizer(fieldName, ".");
        String firstComponent = st.nextToken();
        if (!PersRTTIUtil.fieldExistsAndIsAColumn(jp.getSelectClass(), firstComponent))
          throw new RuntimeException("field '"+fieldName+"' of class '"+jp.getSelectClass().getSimpleName()+"' does not exist or is not a column");
        jp.push(new FComponent(jp.getAlias(), fieldName));
        return new FAfter(jp);
    }
}