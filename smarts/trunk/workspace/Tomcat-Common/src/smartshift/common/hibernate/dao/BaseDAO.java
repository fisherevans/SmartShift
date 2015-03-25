package smartshift.common.hibernate.dao;

import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.DeleteByIDTask;
import smartshift.common.hibernate.dao.tasks.DeleteTask;
import smartshift.common.hibernate.dao.tasks.ListNamedQueryTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.hibernate.dao.tasks.RowCountTask;
import smartshift.common.hibernate.dao.tasks.UniqueByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.UniqueByIDTask;
import smartshift.common.hibernate.dao.tasks.UniqueNamedQueryTask;
import smartshift.common.hibernate.dao.tasks.UpdateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> the model this DAO fetches
 *
 */
public abstract class BaseDAO<T> {
    private DAOContext _daoContext;
    
    private final Class<T> _modelClass;
    
    /**
     * Initializes the object.
     * @param modelClass
     */
    public BaseDAO(DAOContext daoContext, Class<T> modelClass) {
        _daoContext = daoContext;
        _modelClass = modelClass;
    }
    
    /** creates a list task that can be executed
     * @param criterions any requirements
     * @return the task object
     */
    public ListTask<T> list(Criterion ... criterions) {
        return new ListTask<T>(this, criterions);
    }
    
    /** creates a list task that can be executed
     * @param criterions any requirements
     * @return the task object
     */
    public RowCountTask<T> rowCount(Criterion ... criterions) {
        return new RowCountTask<T>(this, criterions);
    }
    
    /** Gets a task that fetches a unique model by id
     * @param id the id
     * @return the task object
     */
    public UniqueByIDTask<T> uniqueByID(Serializable id) {
        return new UniqueByIDTask<T>(this, id);
    }
    
    /** Gets a task that fetches a unique model by criterion
     * @param criterions the restrictions
     * @return the task object
     */
    public UniqueByCriteriaTask<T> uniqueByCriteria(Criterion ... criterions) {
        return new UniqueByCriteriaTask<T>(this, criterions);
    }
    
    /** Gets a task that adds a new model to the DB
     * @param model the newly made model to add
     * @return the task object
     */
    public AddTask<T> add(T model) {
        return new AddTask<T>(this, model);
    }

    /** Gets a task that updates an existing model
     * @param model the model to update from the proxy
     * @return the task object
     */
    public UpdateTask<T> update(T model) {
        return new UpdateTask<T>(this, model);
    }

    /** Gets a task that deletes all models by criteria
     * @param criterions the restrictions for models to delete
     * @return the task object
     */
    public DeleteByCriteriaTask<T> deleteByCriteria(Criterion ... criterions)  {
        return new DeleteByCriteriaTask<T>(this, criterions);
    }
    
    /** Gets a task that deletes a unique model by its id
     * @param id the id of the model
     * @return the task object
     */
    public DeleteByIDTask<T> deleteByID(Serializable id)  {
        return new DeleteByIDTask<T>(this, id);
    }
    
    /** Gets a task that deletes a model
     * @param model the model to delete, must exist/have been fetched
     * @return the task object
     */
    public DeleteTask<T> delete(T model) {
        return new DeleteTask<T>(this, model);
    }
    
    /** Gets a task that calls a named query and returns the list result
     * @param queryName the named query
     * @param parameters the parameters
     * @return the task object
     */
    public ListNamedQueryTask<T> listNamedQuery(String queryName, NamedParameter ... parameters) {
        return new ListNamedQueryTask<T>(this, queryName, parameters);
    }
    
    /** Gets a task that calls a named query and returns the unique result
     * @param queryName the named query
     * @param parameters the parameters
     * @return the task object
     */
    public UniqueNamedQueryTask<T> uniqueNamedQuery(String queryName, NamedParameter ... parameters) {
        return new UniqueNamedQueryTask<T>(this, queryName, parameters);
    }
    
    /** A helper methods that prepares a named query statement
     * @param session the session to construct the query with
     * @param queryName the name of the named query
     * @param parameters any parameters this query should us
     * @return the Query object
     */
    public Query prepareNamedQuery(Session session, String queryName, NamedParameter ... parameters) {
        getLogger().debug("prepareNamedQuery() enter. Name: " + queryName);
        Query query = session.getNamedQuery(queryName);
        for(NamedParameter parameter:parameters) {
            getLogger().debug("prepareNamedQuery() Param: " + parameter.parameterName + ", " + parameter.value);
            if(parameter.parameterName instanceof String)
                query.setParameter((String) parameter.parameterName, parameter.value);
            else
                query.setParameter((Integer) parameter.parameterName, parameter.value);
        }
        getLogger().debug("prepareNamedQuery() exit.");
        return query;
    }
    
    /**
     * @return creates and returns a new session based on this dao's context
     */
    public Session getSession() {
        return _daoContext.getSession();
    }
    
    /**
     * @return this DAO's context
     */
    public DAOContext getDAOContext() {
        return _daoContext;
    }
    
    /**
     * @return the model class this dao fetches
     */
    public Class<T> getModelClass() {
        return _modelClass;
    }
    
    protected abstract SmartLogger getLogger();
    
    /**
     * @author "D. Fisher Evans <contact@fisherevans.com>"
     * holds a pair of named parameter and values
     */
    public static class NamedParameter {
        /** the parameter name */
        public final Object parameterName;

        /** the parameter value */
        public final Object value;
        
        /**
         * Initializes the object.
         * @param parameterName the name. Integer or String only
         * @param value the value
         */
        public NamedParameter(Object parameterName, Object value) {
            this.parameterName = parameterName;
            this.value = value;
        }
    }
}
