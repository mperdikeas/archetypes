package gr.neuropublic.base;

import gr.neuropublic.TestApp.entities.A;
import gr.neuropublic.persutil.JPUtil;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import static mutil.base.Util.castToShortWithChecks;


public abstract class AbstractFacade<T> implements IFacade<T> {

    private static final Logger logger = Logger.getLogger(AbstractFacade.class.getName());
    private Class<T> entityClass = returnedClass();
    
    @PersistenceContext(unitName = "TestAppPU" ,type=PersistenceContextType.EXTENDED)
    protected EntityManager em;

    protected abstract EntityManager getEntityManager();

    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    public void merge(T entity) {
        getEntityManager().merge(entity);
    }

    protected Class<T> returnedClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public void flush()
    {
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findAllByCriteriaRange(Map params) {
        return findAllByCriteriaRange(params, null, null, null, false);
    }

    // TODO this method should be simplified
    public List<T> findAllByCriteriaRange(Map params, int[] range, int[] recordCount, String sortField, boolean sortOrder) {

        //Initialize query String
        StringBuilder queryStr = new StringBuilder("SELECT e FROM " + entityClass.getSimpleName() + " e ");
        StringBuilder countQueryStr = new StringBuilder("SELECT count(e) FROM " + entityClass.getSimpleName() + " e ");

        //If parameter list is not empty construct whereClause with multiple query parameters
        if (!params.isEmpty()) {

            StringBuilder whereClause = constructWhereClause(params);
            queryStr.append(whereClause);
            countQueryStr.append(whereClause);
        }

        //Set up sorting
        if (sortField != null) {
            String orderStr = sortOrder ? " asc" : " desc";
            queryStr.append(" order by e.");
            queryStr.append(sortField);
            queryStr.append(orderStr);
        }

        //Create queries
        Query query = getEntityManager().createQuery(queryStr.toString());
        Query countQuery = getEntityManager().createQuery(countQueryStr.toString());

        //Find the total record count and add to the row count
        if (recordCount != null) {
            recordCount[0] += ((Long) countQuery.getSingleResult()).intValue();
        }

        //Set up the pagination range
        if (range != null) {
            query.setMaxResults(range[1] - range[0]);
            query.setFirstResult(range[0]);
        }

        return query.getResultList();
    }

    private StringBuilder constructWhereClause(Map params) {
        StringBuilder whereClause = new StringBuilder();
        int paramIndex = 1;

        whereClause.append(" WHERE ");

        for (Object paramName : params.keySet()) {
            if (paramIndex > 1) {
                whereClause.append(" AND ");
            }
            if (paramName.equals("specialAndClause")) {
                whereClause.append(params.get(paramName));
            }
            else {
                whereClause.append(" e.");
                whereClause.append(paramName);
                whereClause.append(" = '");
                whereClause.append(params.get(paramName));
                whereClause.append("'");
            }

            paramIndex++;
        }

        return whereClause;
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }


//    public short getSeqNextValAsShort() {return castToShortWithChecks(getSeqNextVal()); }
//    public int   getSeqNextValAsInt()   {return getSeqNextVal(); }
//    public long  getSeqNextValAsLong()  {return getSeqNextVal(); }

//    private Integer getSeqNextVal() {
//        return JPUtil.getNextSequenceValueForEntity(entityClass, getEntityManager());
//    }

    public abstract T initRow();
    
    
    
    /**
     * 
     * @param rowsForInsert
     * @param rowsForDelete
     * @param rowsForUpdate 
     */
    public void synchronizeChangesWithDb(List<T> rowsForInsert,List<T> rowsForDelete,List<T> rowsForUpdate)
    { 
        for (T t : rowsForInsert) {
            logger.info("Persisting T "+ t.toString());
            em.persist(t);
        }

        for (T t : rowsForDelete) {
            logger.info("Removing T "+ t.toString());
            em.remove(t);
        }

        for (T t : rowsForUpdate) {
            logger.info("Updating T " + t.toString());
            //em.merge(t); //This is not needed. Entities are already managed
        }
        
        em.flush();
    }
}