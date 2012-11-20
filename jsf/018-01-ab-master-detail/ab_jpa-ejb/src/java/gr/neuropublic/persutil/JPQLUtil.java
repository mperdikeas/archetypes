package gr.neuropublic.persutil;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import mutil.base.Pair;

public class JPQLUtil
{

    public static Integer incrementByOne(Integer i) {
        if (i==null) return null;
        else return i+1;
    }

    /*
    public static <T> QualifiedResultList<T> getResults(EntityManager em, String jpql) {
        return getResults(em, jpql, null);
    }

    public static <T> QualifiedResultList<T> getResults(EntityManager em, String jpql, Integer recordLimit) {
        Query q = em.createQuery(jpql);
        if (recordLimit != null) q.setMaxResults(incrementByOne(recordLimit));
        return new QualifiedResultList<T>((List<T>) q.getResultList(), recordLimit);
        }*/

    public static <T> QualifiedResultList<T> getResults(EntityManager em, Pair<String, Map<String, Object>> jpql_and_params) {
        return getResults(em, jpql_and_params, null);
    }

    public static <T> QualifiedResultList<T> getResults(EntityManager em, Pair<String, Map<String, Object>> jpql_and_params, Integer recordLimit) {
        Query q = em.createQuery(jpql_and_params.a);
        for (String key : jpql_and_params.b.keySet()) {
            q.setParameter(key, jpql_and_params.b.get(key));
        }
        if (recordLimit != null) q.setMaxResults(incrementByOne(recordLimit));
        return new QualifiedResultList<T>((List<T>) q.getResultList(), recordLimit);
    }


}