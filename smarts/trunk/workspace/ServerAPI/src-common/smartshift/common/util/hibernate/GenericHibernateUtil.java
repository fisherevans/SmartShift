package smartshift.common.util.hibernate;

import java.io.Serializable;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.json.APIResultFactory;

/**
 * A set of utilities for generic lookups with hibernate
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class GenericHibernateUtil {
    private static Logger logger = Logger.getLogger(GenericHibernateUtil.class);

    /**
     * Get a unique object based on an ID
     * 
     * @param clazz The table entity class
     * @param id the id
     * @return the unique object
     * @throws WebApplicationException if it's a bad request or other general
     * error
     */
    public static Object getUniqueObjectJson(Class clazz, Serializable id) throws WebApplicationException {
        Session session = null;
        Object object = null;
        try {
            session = HibernateFactory.getSession("smartshift");
            object = session.load(clazz, id);
            Hibernate.initialize(object);
        } catch(ObjectNotFoundException e) {
            String result = clazz.getCanonicalName() + " not found with the ID of " + id.toString();
            logger.error(result, e);
            throw APIResultFactory.getException(Status.BAD_REQUEST, result);
        } catch(Exception e) {
            logger.error("Failed to fetch " + clazz.getCanonicalName(), e);
            throw APIResultFactory.getException(Status.INTERNAL_SERVER_ERROR);
        } finally {
            logger.debug("Closing session...");
            if(session != null)
                session.close();
        }
        return object;
    }

    /**
     * Get a list of objects based based on a set of restrictions
     * 
     * @param clazz The table entity class
     * @param criterions the set of restrictions
     * @return the list of objects
     * @throws WebApplicationException if it's a bad request or other general
     * error
     */
    public static List<Object> getObjectListJson(Class clazz, Criterion... criterions) {
        Session session = null;
        List<Object> objects = null;
        try {
            session = HibernateFactory.getSession("smartshift");
            Criteria criteria = session.createCriteria(clazz);
            for(Criterion criterion : criterions)
                criteria.add(criterion);
            criteria.setCacheable(true);
            objects = criteria.list();
        } catch(Exception e) {
            logger.error("Failed to fetch the list of " + clazz.getCanonicalName(), e);
            throw APIResultFactory.getException(Status.INTERNAL_SERVER_ERROR);
        } finally {
            logger.debug("Closing session...");
            if(session != null)
                session.close();
        }
        return objects;
    }
}
