package gr.neuropublic.persutil;

import java.util.Date;

public class FAfter {
    private JPQLBuilder jp;
    public FAfter(JPQLBuilder jp) {
        this.jp = jp;
    }
    public IsAfter is(String value) {
        jp.push(new IsComponent(value));
        return new IsAfter(jp);
    }
    public IsAfter is(Integer value) {
        jp.push(new IsIntegerComponent(value));
        return new IsAfter(jp);
    }
    public IsAfter like(String value) {
        jp.push(new LikeComponent(value));
        return new IsAfter(jp);
    }
    public IsAfter is(Boolean value) {
        jp.push(new IsBooleanComponent(value));
        return new IsAfter(jp);
    }
    public IsAfter beforeInclsv(Date value) {
        jp.push(new BeforeComponent(value));
        return new IsAfter(jp);
    }
    public IsAfter afterInclsv(Date value) {
        jp.push(new AfterComponent(value));
        return new IsAfter(jp);
    }

}
