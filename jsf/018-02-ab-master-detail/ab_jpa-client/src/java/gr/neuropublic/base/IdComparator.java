/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.neuropublic.base;


import gr.neuropublic.base.Identifiable;
import java.util.Comparator;

/**
 *
 * @author v_asloglou
 */
public class IdComparator implements Comparator
{

    public int compare(Object o1, Object o2)
    {

        Integer o1Id = ((Identifiable) o1).getId();
        Integer o2Id = ((Identifiable) o2).getId();

        return o1Id.compareTo(o2Id);

    }
}
