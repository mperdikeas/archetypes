package a.b.c.typeA.dbdal;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

public class Person {
    public int i;
    public String fname;
    public String lname;
    public String comments;
    public int yearOfBirth;

    public Person(int i, String fname, String lname, String comments, int yearOfBirth) {
        this.i = i;
        this.fname = fname;
        this.lname = lname;
        this.comments = comments;
        this.yearOfBirth = yearOfBirth;
    }

    // we need the no-arg constructor for JSON POST requests where the Java object has to be
    // created out of the JSON serialization
    public Person() {
    }

    protected ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
            .add("i", i)
            .add("fname", fname)
            .add("lname", lname)
            .add("comments", comments)
            .add("yearOfBirth", yearOfBirth);
    }

    @Override
    public final String toString() {
        return toStringHelper().toString();
    }
    
    /* The above pattern (https://github.com/google/guava/issues/1239)
       allows toStringHelper to be extended in derived
       classes as follows:

             @Override
             protected ToStringHelper toStringHelper() {
                 return super.toStringHelper()
                     .add("baz", baz)
                     .add("qux", qux);
             }
    */
}
