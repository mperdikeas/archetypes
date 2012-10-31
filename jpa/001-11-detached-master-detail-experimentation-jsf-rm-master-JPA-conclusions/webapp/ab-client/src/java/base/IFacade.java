package base;


import java.util.List;
import java.util.Map;

public interface IFacade<T> {
    List<T> findAll();

    public void persist(T object);
    public T merge(T object);
    public void refresh(T t);
    public T find  (Object id);
    public void remove(T object);
    public boolean emContains(T t);
    
    public List<T> findRange(int[] range);
    public List<T> findAllByCriteriaRange(Map params);
    public List<T> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder);
    
    public int count();


    public short getSeqNextValAsShort();
    public int   getSeqNextValAsInt();
    public long  getSeqNextValAsLong();
}
