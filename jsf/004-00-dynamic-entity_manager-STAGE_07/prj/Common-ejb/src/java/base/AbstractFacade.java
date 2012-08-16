package base;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import java.lang.reflect.ParameterizedType;
import javax.persistence.Query;


public abstract class AbstractFacade<T> {

    private static Logger l = Logger.getLogger(AbstractFacade.class.getName());
    private Class<T> entityClass;
    protected abstract EntityManager getEntityManager();
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    private Class returnedClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[0];
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

        if (range == null || range.length != 2) {
            throw new IllegalArgumentException("Pagination range should have two values: min and max");
        }
        if (recordCount == null || recordCount.length != 1) {
            throw new IllegalArgumentException("Pagination recordCount should have only one value");
        }

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

        //Set up query parameter values dynamically
        int paramIndex = 1;
        for (Object key : params.keySet()) {
            Object value = params.get(key);
            query.setParameter(paramIndex, value);
            countQuery.setParameter(paramIndex, value);
            paramIndex++;
        }

        //Find the total record count
        recordCount[0] = ((Long) countQuery.getSingleResult()).intValue();

        //Set up the pagination range
        query.setMaxResults(range[1] - range[0]);
        query.setFirstResult(range[0]);

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

            whereClause.append(" e.");
            whereClause.append(paramName);
            whereClause.append(" = ?");
            whereClause.append(paramIndex);

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
}
