package smartshift.common.hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> the model this DAO fetches
 *
 */
public abstract class BaseDAO<T> {
    private final Class<T> _modelClass;
    
    /**
     * Initializes the object.
     * @param modelClass
     */
    public BaseDAO(Class<T> modelClass) {
        _modelClass = modelClass;
    }
    
    /** Gets a list of the model
     * @param criterions any criteria required
     * @return the list of models
     */
    public ROCollection<T> list(Criterion ... criterions) {
        getLogger().debug("list() enter. Criterions: " + criterions.length);
        List<T> models = GenericHibernateUtil.list(getSession(), _modelClass, criterions);
        getLogger().debug("list() exit. Got: " + models.size());
        return ROCollection.wrap(models);
    }
    
    /** Gets a unique model by id
     * @param id the id
     * @return the model, null if not found
     */
    public T uniqueByID(Serializable id) {
        getLogger().debug("uniqueByID() enter. ID: " + id);
        T model = GenericHibernateUtil.unique(getSession(), _modelClass, id);
        getLogger().debug("uniqueByID() exit. Got: " + model);
        return model;
    }
    
    /** Gets a unique model by a set of criteria
     * @param criterions the restrictions
     * @return the model. null if not found.
     */
    public T uniqueByCriteria(Criterion ... criterions) {
        getLogger().debug("uniqueByCriteria() enter. Criterions: " + criterions.length);
        T model = GenericHibernateUtil.uniqueByCriterea(getSession(), _modelClass, criterions);
        getLogger().debug("uniqueByCriteria() exit. Got: " + model);
        return model;
    }
    
    /** adds a model. Must not already exist
     * @param model the pre made model to add
     * @return the passed model after being saved
     * @throws DBException if a DB occurrs
     */
    public T add(T model) throws DBException {
        getLogger().debug("add() enter. Model: " + model);
        GenericHibernateUtil.save(getSession(), model);
        getLogger().debug("add() exit. added.");
        return model;
    }
    
    /** updates an existing model
     * @param model the model to update from the proxy
     * @return the updated model
     * @throws DBException if the db does something
     */
    public T update(T model) throws DBException {
        getLogger().debug("update() enter. Model: " + model);
        GenericHibernateUtil.update(getSession(), model);
        getLogger().debug("update() exit. updated.");
        return model;
    }
    
    /** delete all model by criteria
     * @param criterions the restrictions for models to delete
     * @throws DBException if an error occurrs
     */
    public void deleteAllByCriteria(Criterion ... criterions) throws DBException {
        getLogger().debug("deleteAllByCriteria() enter. Criterions: " + criterions.length);
        ROCollection<T> models = list(criterions);
        getLogger().debug("deleteAllByCriteria() Models: " + models.size());
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            for(T model:models) {
                GenericHibernateUtil.delete(getSession(), model, false);
            }
        } catch(Exception e) {
            getLogger().warn("deleteAllByCriteria() Got error: " + e.getLocalizedMessage());
            transaction.rollback();
            throw e;
        }
        transaction.commit();
        getLogger().debug("deleteAllByCriteria() exit.");
    }
    
    /** deletes a single model by first fetching it by id
     * @param id the id of the model
     * @throws DBException if an error occurs
     */
    public void deleteById(Serializable id) throws DBException {
        getLogger().debug("deleteById() enter. ID: " + id);
        delete(uniqueByID(id));
        getLogger().debug("deleteById() exit.");
    }
    
    /** deletes a model
     * @param model the model to delete, must exist/have been fetched
     * @throws DBException if an error occurrs
     */
    public void delete(T model) throws DBException {
        getLogger().debug("delete() enter. Model: " + model);
        GenericHibernateUtil.delete(getSession(), model);
        getLogger().debug("delete() exit.");
    }
    
    /** calls a named query and returns the list result
     * @param queryName the named query
     * @param parameters the parameters
     * @return the list of models
     */
    public ROCollection<T> listNamedQuery(String queryName, NamedParameter ... parameters) {
        getLogger().debug("listNamedQuery() enter.");
        Query query = prepareNamedQuery(queryName, parameters);
        @SuppressWarnings("unchecked")
        List<T> models = query.list();
        getLogger().debug("listNamedQuery() exit. Got: " + models.size());
        return ROCollection.wrap(models);
    }
    
    /** calls a named query and returns the unique result
     * @param queryName the named query
     * @param parameters the params
     * @return the unique result, null if not found
     */
    public T uniqueNamedQuery(String queryName, NamedParameter ... parameters) {
        getLogger().debug("uniqueNamedQuery() enter.");
        Query query = prepareNamedQuery(queryName, parameters);
        @SuppressWarnings("unchecked")
        T model = (T) query.uniqueResult();
        getLogger().debug("uniqueNamedQuery() exit. Got: " + model);
        return model;
    }
    
    protected Query prepareNamedQuery(String queryName, NamedParameter ... parameters) {
        getLogger().debug("prepareNamedQuery() enter. Name: " + queryName);
        Query query = getSession().getNamedQuery(queryName);
        for(NamedParameter parameter:parameters) {
            getLogger().debug("prepareNamedQuery() Param: " + parameter.key + ", " + parameter.value);
            if(parameter.key instanceof String)
                query.setParameter((String) parameter.key, parameter.value);
            else
                query.setParameter((Integer) parameter.key, parameter.value);
        }
        getLogger().debug("prepareNamedQuery() exit.");
        return query;
    }
    
    protected abstract Session getSession();
    
    protected abstract SmartLogger getLogger();
    
    /**
     * @author "D. Fisher Evans <contact@fisherevans.com>"
     * holds a pair of named parameter and values
     */
    public static class NamedParameter {
        /** the parameter name */
        public final Object key;

        /** the parameter value */
        public final Object value;
        
        /**
         * Initializes the object.
         * @param key the name. Integer or String
         * @param value the value
         */
        public NamedParameter(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
