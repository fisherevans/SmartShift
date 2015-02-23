package smartshift.common.hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.util.collections.ROCollection;
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
    @SuppressWarnings("unchecked")
    public ROCollection<T> list(Criterion ... criterions) {
        getLogger().debug("list() enter. Criterions: " + criterions.length);
        DBAction action = new DBAction(getSession());
        try {
            Criteria criteria = action.session().createCriteria(_modelClass);
            for(Criterion criterion : criterions)
                criteria.add(criterion);
            List<T> models = criteria.list();
            action.commit();
            getLogger().debug("list() exit. Got: " + (models == null ? null : models.size()));
            return ROCollection.wrap(models);
        } catch(Exception e) {
            getLogger().warn("list() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** Gets a unique model by id
     * @param id the id
     * @return the model, null if not found
     */
    @SuppressWarnings("unchecked")
    public T uniqueByID(Serializable id) {
        getLogger().debug("uniqueByID() enter. ID: " + id);
        DBAction action = new DBAction(getSession());
        try {
            T model = (T) action.session().get(_modelClass, id);
            action.commit();
            getLogger().debug("uniqueByID() exit. Got: " + model);
            return model;
        } catch(Exception e) {
            getLogger().warn("uniqueByID() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** Gets a unique model by a set of criteria
     * @param criterions the restrictions
     * @return the model. null if not found.
     */
    @SuppressWarnings("unchecked")
    public T uniqueByCriteria(Criterion ... criterions) {
        getLogger().debug("uniqueByCriteria() enter. Criterions: " + criterions.length);
        DBAction action = new DBAction(getSession());
        try {
            Criteria criteria = action.session().createCriteria(_modelClass);
            for(Criterion criterion : criterions)
                criteria.add(criterion);
            T model = (T) criteria.uniqueResult();
            action.commit();
            getLogger().debug("uniqueByCriteria() exit. Got: " + model);
            return model;
        } catch(Exception e) {
            getLogger().warn("uniqueByCriteria() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** adds a model. Must not already exist
     * @param model the pre made model to add
     * @return the passed model after being saved
     * @throws DBException if a DB occurrs
     */
    public T add(T model) throws DBException {
        getLogger().debug("add() enter. Model: " + model);
        DBAction action = new DBAction(getSession());
        try {
            action.session().save(model);
            action.commit();
            getLogger().debug("add() exit. Got: " + model);
            return model;
        } catch(Exception e) {
            getLogger().warn("add() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** updates an existing model
     * @param model the model to update from the proxy
     * @return the updated model
     * @throws DBException if the db does something
     */
    public T update(T model) throws DBException {
        getLogger().debug("add() enter. Model: " + model);
        DBAction action = new DBAction(getSession());
        try {
            action.session().update(model);
            action.commit();
            getLogger().debug("add() exit. Got: " + model);
            return model;
        } catch(Exception e) {
            getLogger().warn("add() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** delete all model by criteria
     * @param criterions the restrictions for models to delete
     * @throws DBException if an error occurrs
     */
    public void deleteAllByCriteria(Criterion ... criterions) throws DBException {
        getLogger().debug("deleteAllByCriteria() enter. Criterions: " + criterions.length);
        ROCollection<T> models = list(criterions);
        DBAction action = new DBAction(getSession());
        try {
            for(T model:models)
                action.session().delete(model);
            action.commit();
            getLogger().debug("deleteAllByCriteria() exit.");
        } catch(Exception e) {
            getLogger().warn("deleteAllByCriteria() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
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
        DBAction action = new DBAction(getSession());
        try {
            action.session().delete(model);
            action.commit();
            getLogger().debug("delete() exit.");
        } catch(Exception e) {
            getLogger().warn("delete() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** calls a named query and returns the list result
     * @param queryName the named query
     * @param parameters the parameters
     * @return the list of models
     */
    @SuppressWarnings("unchecked")
    public ROCollection<T> listNamedQuery(String queryName, NamedParameter ... parameters) {
        getLogger().debug("listNamedQuery() enter.");
        DBAction action = new DBAction(getSession());
        try {
            Query query = prepareNamedQuery(action.session(), queryName, parameters);
            List<T> models = query.list();
            action.commit();
            getLogger().debug("listNamedQuery() exit. Got: " + (models == null ? null : models.size()));
            return ROCollection.wrap(models);
        } catch(Exception e) {
            getLogger().warn("listNamedQuery() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    /** calls a named query and returns the unique result
     * @param queryName the named query
     * @param parameters the params
     * @return the unique result, null if not found
     */
    @SuppressWarnings("unchecked")
    public T uniqueNamedQuery(String queryName, NamedParameter ... parameters) {
        getLogger().debug("uniqueNamedQuery() enter.");
        DBAction action = new DBAction(getSession());
        try {
            Query query = prepareNamedQuery(action.session(), queryName, parameters);
            T model = (T) query.uniqueResult();
            action.commit();
            getLogger().debug("uniqueNamedQuery() exit. Got: " + model);
            return model;
        } catch(Exception e) {
            getLogger().warn("uniqueNamedQuery() Got exception! - " + e.getLocalizedMessage());
            action.rolback();
            throw e;
        }
    }
    
    protected Query prepareNamedQuery(Session session, String queryName, NamedParameter ... parameters) {
        getLogger().debug("prepareNamedQuery() enter. Name: " + queryName);
        Query query = session.getNamedQuery(queryName);
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
