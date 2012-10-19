package backingbeans;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DivisorSet {

    private static final Logger l = Logger.getLogger(DivisorSet.class.getName());

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

    public Integer getEssence() { return number; }


    //    public String toString() { return String.valueOf(number); }

    public String toString() { return String.valueOf(number)+ "( "+ System.identityHashCode(this)+") "; }
    /*
    public boolean equals(Object otherO) {
        l.info(String.format("################ comparing %d against %d", System.identityHashCode(this), System.identityHashCode(otherO)));
        if (otherO==null)                                                 return false;
        if (!(otherO instanceof DivisorSet))                              return false;
        DivisorSet other = (DivisorSet) otherO;
        if ((this.number != null) && (other.number == null))              return false;
        if ((this.number != null) && (!this.number.equals(other.number))) return false;
        return true;
        }*/
}