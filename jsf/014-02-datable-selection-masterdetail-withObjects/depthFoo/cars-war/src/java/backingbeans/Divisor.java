package backingbeans;
import java.util.List;
import java.util.ArrayList;

public class Divisor {

    public Divisor(Integer number) {
        this.number = number;
    }
    public Integer number;

    public String toString() { return String.valueOf(number)+" ( "+System.identityHashCode(this)+" ) " ; }

}