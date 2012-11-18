package gr.neuropublic.persutil;

import java.util.List;

public class QualifiedResultList<T> {
    public List<T> data;
    public Integer limit;
    public boolean theresMore;

    public QualifiedResultList(List<T> data, Integer limit) {
        this.data = data;
        if (limit != null) {
            this.limit = limit;
            if (data.size()>limit) {
                if (data.size() != limit+1) throw new RuntimeException("this class is built with the assumption"+
                                                                       " that the SQL query is cut-off at *exactly* limit+1");
                else {
                    this.data.remove(data.size()-1);
                    this.theresMore = true;
                }
            } else {
                this.theresMore = false;
            }
        } else {
            this.limit = null;
            this.theresMore = false;
        }
        panicIfMalformed();
    }

    private void panicIfMalformed() {
        if (data==null) throw new RuntimeException();
        if ((limit==null)&&(theresMore)) throw new RuntimeException();
        if (limit!=null) {
            if (data.size()>limit) throw new RuntimeException();
            if ((data.size()<limit)&&(theresMore)) throw new RuntimeException();
        }
    }


}