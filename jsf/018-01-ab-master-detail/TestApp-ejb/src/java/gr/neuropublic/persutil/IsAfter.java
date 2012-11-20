package gr.neuropublic.persutil;

public class IsAfter {
    private JPQLBuilder jp;
    public IsAfter(JPQLBuilder jp) {
        this.jp = jp;
    }

    public F and() {
        jp.push(new AndComponent());
        return new F(jp);
    }
    public Ready end() {
        jp.push(new EndComponent());
        return new Ready(jp);
    }
}