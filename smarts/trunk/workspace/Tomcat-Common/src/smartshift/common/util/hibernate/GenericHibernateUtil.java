package smartshift.common.util.hibernate;

import java.io.Serializable;
import java.util.List;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;
import smartshift.common.hibernate.DBError;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.util.json.APIResultFactory;

/**
 * A set of utilities for generic lookups with hibernate
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class GenericHibernateUtil {
    private static Logger logger = Logger.getLogger(GenericHibernateUtil.class);

    public static <T> T unique(Session session, Class clazz, Serializable id) throws WebApplicationException {
        T object = null;
        try {
            object = (T) session.load(clazz, id);
        } catch(Exception e) {
            logger.error("Failed to fetch unique by id " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR));
        }
        return object;
    }
    
    public static <T> T uniqueByCriterea(Session session, Class clazz, Criterion ... criterions) throws WebApplicationException {
        T object = null;
        try {
            Criteria crit = session.createCriteria(clazz);
            for(Criterion criterion:criterions)
                crit.add(criterion);
            object = (T) crit.uniqueResult();
        } catch(Exception e) {
            logger.error("Failed to fetch unique with crits " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR));
        }
        return object;
    }
    
    public static <T> List<T> list(Session session, Class clazz, Criterion... criterions) {
        List<T> objects = null;
        try {
            Criteria criteria = session.createCriteria(clazz);
            for(Criterion criterion : criterions)
                criteria.add(criterion);
            criteria.setCacheable(true);
            objects = criteria.list();
        } catch(Exception e) {
            logger.error("Failed to fetch the list of " + clazz.getCanonicalName(), e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR));
        }
        return objects;
    }
    
    public static void save(Session session, Object object) throws DBException {
        try {
            session.getTransaction().begin();
            session.save(object);
        } catch(PropertyValueException e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new DBException(DBError.BadData, "Invalid property value: " + e.getPropertyName());
        } catch(ConstraintViolationException e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new DBException(DBError.ConstraintError, e.getCause().getMessage());
        } catch(Exception e) {
            logger.error("Failed to add object " + object.getClass().toString(), e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR));
        }
    }
}
