package smartshift.common.util.hibernate;

import java.io.Serializable;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;
import smartshift.common.hibernate.DBError;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A set of utilities for generic lookups with hibernate
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class GenericHibernateUtil {
    private static SmartLogger logger = new SmartLogger(GenericHibernateUtil.class);

    /**
     * Fetches a unique object from the database by ID
     * @param <T> The type of object
     * @param session the session to use
     * @param clazz the class of the object (should be same as Type)
     * @param id the primary ID of the object
     * @return the fetched object
     * @throws WebApplicationException if the object is not found
     */
    @SuppressWarnings("unchecked")
    public static <T> T unique(Session session, Class<T> clazz, Serializable id) throws WebApplicationException {
        // TODO load should only be used if we know the object exists
        T object = null;
        try {
            object = (T) session.get(clazz, id);
        } catch(Exception e) {
            logger.error("Failed to fetch unique by id " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(getInternalError("Failed to fetch object"));
        }
        return object;
    }

    /**
     * Fetches a unique object from the database by qualifying crits
     * @param <T> The type of object
     * @param session the session to use
     * @param clazz the class of the object (should be same as Type)
     * @param criterions an array of Hibernate critereons to base this query on
     * @return the fetched object, null if not found
     * @throws WebApplicationException if the object is not found
     */
    @SuppressWarnings("unchecked")
    public static <T> T uniqueByCriterea(Session session, Class<T> clazz, Criterion ... criterions) throws WebApplicationException {
        T object = null;
        try {
            Criteria crit = session.createCriteria(clazz);
            for(Criterion criterion:criterions)
                crit.add(criterion);
            // Unchecked
            object = (T) crit.uniqueResult();
        } catch(Exception e) {
            logger.error("Failed to fetch unique with crits " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(getInternalError("Failed to fetch object"));
        }
        return object;
    }

    /**
     * Fetches a set of objects from the database by qualifying crits
     * @param <T> The type of objects
     * @param session the session to use
     * @param clazz the class of the objects (should be same as Type)
     * @param criterions an array of Hibernate critereons to base this query on
     * @return the fetched object set
     * @throws WebApplicationException if there was an error fetching the set
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> list(Session session, Class<T> clazz, Criterion... criterions) {
        List<T> objects = null;
        try {
            Criteria criteria = session.createCriteria(clazz);
            for(Criterion criterion : criterions)
                criteria.add(criterion);
            criteria.setCacheable(true);
            // Unchecked
            objects = criteria.list();
        } catch(Exception e) {
            logger.error("Failed to fetch the list of " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(getInternalError("Failed to fetch list of objects"));
        }
        return objects;
    }
    
    /**
     * @see save(Session, Object, boolean)
     * calls with a true commit
     */
    public static Object save(Session session, Object object) throws DBException {
        return save(session, object, true);
    }
    
    /**
     * Saves a new object
     * @param session the session to use
     * @param object the existing or new object to save
     * @return the id of the saved object
     * @throws DBException If the request is bad
     */
    public static Object save(Session session, Object object, boolean commit) throws DBException {
        Transaction tx = null;
        try {
            if(commit)
                tx = session.beginTransaction();
            Object id = session.save(object);
            if(commit) {
                tx.commit();
                tx = null;
            }
            logger.debug("Saved new " + Object.class.getSimpleName() + " with the ID of: " + id.toString());
            return id;
        } catch(PropertyValueException e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new DBException(DBError.BadData, "Invalid property value: " + e.getPropertyName());
        } catch(ConstraintViolationException e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new DBException(DBError.ConstraintError, e.getCause().getMessage());
        } catch(Exception e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new WebApplicationException(getInternalError("Failed to save object"));
        } finally {
            if(commit && tx != null)
                tx.rollback();
        }
    }
    
    /**
     * @see update(Session, Object, boolean)
     * calls with a true commit
     */
    public static void update(Session session, Object object) throws DBException {
        update(session, object, true);
    }
    
    /**
     * updates an existing object
     * @param session the session to use
     * @param object the existing or new object to save
     * @throws DBException If the request is bad
     */
    public static void update(Session session, Object object, boolean commit) throws DBException {
        Transaction tx = null;
        try {
            if(commit)
                tx = session.beginTransaction();
            session.update(object);
            if(commit) {
                tx.commit();
                tx = null;
            }
        } catch(PropertyValueException e) {
            logger.error("Failed to update object " + object.getClass().toString(), e);
            throw new DBException(DBError.BadData, "Invalid property value: " + e.getPropertyName());
        } catch(ConstraintViolationException e) {
            logger.error("Failed to update object " + object.getClass().toString(), e);
            throw new DBException(DBError.ConstraintError, e.getCause().getMessage());
        } catch(Exception e) {
            logger.error("Failed to update object " + object.getClass().toString(), e);
            throw new WebApplicationException(getInternalError("Failed to update object"));
        } finally {
            if(commit && tx != null)
                tx.rollback();
        }
    }
    
    /**
     * @param session 
     * @param object 
     * @throws DBException 
     * @see delete(Session, Object, boolean)
     * calls with a true commit
     */
    public static void delete(Session session, Object object) throws DBException {
        delete(session, object, true);
    }
    
    /**
     * deletes an existing object
     * @param session the session to use
     * @param object the existing object to delete
     * @param commit set to true if you want start a tx and commit
     * @throws DBException If the request is bad
     */
    public static void delete(Session session, Object object, boolean commit) throws DBException {
        Transaction tx = null;
        try {
            if(commit)
                tx = session.beginTransaction();
            session.delete(object);
            if(commit) {
                tx.commit();
                tx = null;
            }
        } catch(PropertyValueException e) {
            logger.error("Failed to delete object " + object.getClass().toString(), e);
            throw new DBException(DBError.BadData, "Invalid property value: " + e.getPropertyName());
        } catch(ConstraintViolationException e) {
            logger.error("Failed to delete object " + object.getClass().toString(), e);
            throw new DBException(DBError.ConstraintError, e.getCause().getMessage());
        } catch(Exception e) {
            logger.error("Failed to delete object " + object.getClass().toString(), e);
            throw new WebApplicationException(getInternalError("Failed to save or update object"));
        } finally {
            if(commit && tx != null)
                tx.rollback();
        }
    }
    
    /**
     * helper method to create a 501 error
     * @param message the message to return with the error
     * @return the build response
     */
    private static Response getInternalError(String message) {
        return APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR, null, message);
    }
}
