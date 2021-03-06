package base;

import mutil.jpapersutil.JPUtil;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import static mutil.base.Util.castToShortWithChecks;


public abstract class AbstractFacade<T> implements IFacade<T> {

    private static final Logger l = Logger.getLogger(AbstractFacade.class.getName());
    private Class<T> entityClass = returnedClass();

    protected abstract EntityManager getEntityManager();

    public boolean emContains(T t) { return getEntityManager().contains(t); }

    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    public T merge(T entity) {
        return getEntityManager().merge(entity);
    }

    public void refresh(T entity) { getEntityManager().refresh(entity); }

    protected Class<T> returnedClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
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


    public short getSeqNextValAsShort() {return castToShortWithChecks(getSeqNextVal()); }
    public int   getSeqNextValAsInt()   {return getSeqNextVal(); }
    public long  getSeqNextValAsLong()  {return getSeqNextVal(); }

    private Integer getSeqNextVal() {
        return JPUtil.getNextSequenceValueForEntity(entityClass, getEntityManager());
    }

    // public abstract T initRow();
}