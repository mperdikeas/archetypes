package backingbeans;
import java.util.List;
import java.util.ArrayList;

public class DivisorSet {

    public DivisorSet(Integer number) {
        this.number = number;
    }
    public Integer number;

    public List<Integer> getDivisors() {
        List<Integer> retValue = new ArrayList<Integer>();
        for (int i = 1 ; i < number ; i++)
            if (number % i == 0)
                retValue.add(i);
        return retValue;
    }

    //     public String toString() { return String.valueOf(number); }

    public String toString() { return String.valueOf(number)+ "( "+ System.identityHashCode(this)+") "; }

}