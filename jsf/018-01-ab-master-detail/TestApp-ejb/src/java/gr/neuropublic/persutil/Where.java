package gr.neuropublic.persutil;

public class Where {
    private JPQLBuilder jp;
    public Where(JPQLBuilder jp) {
        this.jp = jp;
    }
    public F where() {
        jp.push(new WhereComponent());
        return new F(jp);
    }
}