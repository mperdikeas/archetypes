package a.b.c.typeA.dbdal;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

public class Person extends PersonBase {
    public int i;

    public Person(int i, String fname, String lname, String comments, int yearOfBirth) {
        super(fname, lname, comments, yearOfBirth);
        this.i = i;
    }

    // we need the no-arg constructor for JSON POST requests where the Java object has to be
    // created out of the JSON serialization
    public Person() {
    }

    protected ToStringHelper toStringHelper() {
        return super.toStringHelper()
            .add("i", i);
    }

    @Override
    public final String toString() {
        return toStringHelper().toString();
    }

}
