package gr.neuropublic.base;


import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author g_dimitriadis
 */
public interface IFacade<T> {
    List<T> findAll();

    public void persist(T object);
    public void merge(T object);
    public T find  (Object id);
    public void remove(T object);
    
    public T initRow();
    
    public List<T> findRange(int[] range);
    public List<T> findAllByCriteriaRange(Map params);
    public List<T> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder);
    
    public int count();


}
