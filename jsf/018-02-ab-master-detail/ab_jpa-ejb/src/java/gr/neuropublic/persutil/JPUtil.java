package gr.neuropublic.persutil;


import java.lang.annotation.Annotation;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains helper methods for Java Persistence
 */
public class JPUtil
{

    private static Logger log = LoggerFactory.getLogger(JPUtil.class);

    /**
     * Overloaded method for {@link createQuery(EntityManager, String, List, Map, String, Integer)}
     */
    public static Query createQuery(EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, String orderClause)
    {
        return createQuery(em, selectClause, whereCriteria, params, orderClause, null);
    }

    /**
     * Dynamicaly creates a query.
     *
     * @param em The entity manager of the session that calls the function.
     * @selectClause The select clause of the query. @whereCriteria A {@link java.Util.ArrayList}
     * list containing a string for each criterion of the query. @params A {@link java.Util.Hashtable}
     * map containing each query parameter mapped by its name in the query.
     * @param orderClause The order by clause of the query. @rerurn The
     * generated java persistence {@link javax.persistence.Query} query.
     */
    public static Query createQuery(EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, String orderClause, Integer recordLimit)
    {
        //construct query string :
        String jpql = getQuerySelect(selectClause, whereCriteria, orderClause);
        // create query and pass parameters
        Query q = em.createQuery(jpql);
        System.out.println("SELECT : " + jpql.toString());
        System.out.println("PARAMS : " + params.toString());
        for (String param : params.keySet())
        {
            q.setParameter(param, params.get(param));
        }
        if (recordLimit != null && recordLimit.intValue() > 0)
        {
            q.setMaxResults(recordLimit);
        }
        return q;
    }

    /**
     * Returns the select clause of a dynamicaly created query.
     */
    public static String getQuerySelect(String selectClause, List<String> whereCriteria, String orderClause)
    {
        // put select clause
        StringBuilder jpql = new StringBuilder(selectClause);
        if (!whereCriteria.isEmpty())
        {
            int i = 1;
            jpql.append(" where ");
            for (String s : whereCriteria)
            {
                if (i++ > 1)
                {
                    jpql.append(" and ");
                }
                jpql.append(s);
            }
        }
        //  put order clause
        if (orderClause != null)
        {
            jpql.append(orderClause);
        }
        return jpql.toString();
    }

    /**
     * Overloaded method of {
     *
     * @see JPUtil#createQuery(EntityManager, String, List<String>, Map<String,
     * Object>, String)}.
     */
    public static Query createQuery(EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params)
    {
        return createQuery(em, selectClause, whereCriteria, params, null);
    }

    /**
     * Gets the results of a dynamicaly created query. Same params as {
     *
     * @see JPUtil#createQuery(EntityManager, String, List<String>, Map<String,
     * Object>, String)} and
     * @param cl The class of the returned objects.
     * @param A {@link java.util.ArrayList} list with the results of the created
     * query.
     */
    public static <T> List<T> getResultList(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, String orderClause, Integer recordLimit)
    {
        Query q = createQuery(em, selectClause, whereCriteria, params, orderClause, recordLimit);
        return (List<T>) q.getResultList();
    }

    /**
     * Overloaded method of {
     *
     * @see JPUtil#getResultList(Class<T>, EntityManager, String, List<String>,
     * Map<String, Object>, String, Integer)}.
     */
    public static <T> List<T> getResultList(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, Integer recordLimit)
    {
        return getResultList(cl, em, selectClause, whereCriteria, params, null, recordLimit);
    }

    /**
     * Overloaded method of {
     *
     * @see JPUtil#getResultList(Class<T>, EntityManager, String, List<String>,
     * Map<String, Object>, String, Integer)}.
     */
    public static <T> List<T> getResultList(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, String orderClause)
    {
        return getResultList(cl, em, selectClause, whereCriteria, params, orderClause, null);
    }

    /**
     * Overloaded method of {
     *
     * @see JPUtil#getResultList(Class<T>, EntityManager, String, List<String>,
     * Map<String, Object>, String, Integer)}.
     */
    public static <T> List<T> getResultList(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params)
    {
        return getResultList(cl, em, selectClause, whereCriteria, params, null, null);
    }

    /**
     * Gets the single result of a dynamicaly created query. Same params as {
     *
     * @see JPUtil#createQuery(EntityManager, String, List<String>, Map<String,
     * Object>, String)} and
     * @param cl The class of the returned object.
     * @param The result of the created query.
     */
    public static <T> T getSingleResult(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params, String orderClause)
    {
        Query q = createQuery(em, selectClause, whereCriteria, params, orderClause);
        return (T) q.getSingleResult();
    }

    /**
     * Overloaded method of {
     *
     * @see JPUtil#getSingleResult(Class<T>, EntityManager, String,
     * List<String>, Map<String, Object>, String)}.
     */
    public static <T> T getSingleResult(Class<T> cl, EntityManager em, String selectClause, List<String> whereCriteria, Map<String, Object> params)
    {
        return getSingleResult(cl, em, selectClause, whereCriteria, params, null);
    }

    static <T> String findIdFieldOfEntity(Class<T> cls)
    {
        for (Field field : cls.getDeclaredFields())
        {
            log.info("field name  = " + field.getName());
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (int i = 0; i < annotations.length; i++)
            {
                if (annotations[i].annotationType().equals(Id.class))
                {
                    log.info("idFieldName = " + name);
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * Returns the name specified on the @Table annotation of the Entity.class
     *
     * @param <T>
     * @param cl
     * @return
     */
    public static <T> String getUnderlyingTableNameForEntity(Class<T> cl)
    {
        log.info("Start of getUnderlyingTableNameForEntity = " + cl.getName());
        String dbTableName = cl.getAnnotation(Table.class).name();
        log.info("End of getUnderlyingTableNameForEntity." + cl.getName() + " dbTableName for entity = " + dbTableName);
        return dbTableName;
    }

    /**
     * Return the sequenceName specified on an @Id field of an Entity.class
     *
     * @param <T>
     * @param cl
     * @return
     */
    public static <T> String getSequenceGeneratorNameForIdFieldOfEntity(Class<T> cl)
    {
        log.info("Start of getSequenceGeneratorNameForIdFieldOfEntity = " + cl.getName());
        String fieldIdName = findIdFieldOfEntity(cl);
        String sequenceName = null;

        try
        {
            Field field = cl.getDeclaredField(fieldIdName);
            sequenceName = field.getAnnotation(SequenceGenerator.class).sequenceName();
        }
        catch (NoSuchFieldException ex)
        {
            ex.printStackTrace();
        }
        catch (SecurityException ex)
        {
            ex.printStackTrace();
        }

        log.info("End of getSequenceGeneratorNameForIdFieldOfEntity. sequenceName = " + sequenceName);
        return sequenceName;
    }

    /**
     * Returns the next value of the sequence which is specified on the
     * @SequenceGenerator on an @Id field of an Entity.class
     *
     * @param <T>
     * @param cl
     * @param em
     * @return
     */
    public static <T> Integer getNextSequenceValueForEntity(Class<T> cl, EntityManager em)
    {
        log.info("Start getNextSequenceValueForEntity" + cl.getName());

        Integer nextVal = null;
        String sequenceName = getSequenceGeneratorNameForIdFieldOfEntity(cl);

        if (sequenceName != null)
        {
            Query query = em.createNativeQuery("SELECT nextval('" + sequenceName + "')");
            BigInteger nextValBI = (BigInteger) query.getSingleResult();
            nextVal = nextValBI.intValue();
        }

        log.info("End of getNextSequenceValueForEntity for Entity " + cl.getName() + " nextVal = " + nextVal);
        return nextVal;
    }
}