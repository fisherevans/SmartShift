package smartshift.common.util.jersey;

import java.io.Serializable;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.json.APIResultFactory;

public class ServiceMethods {
    private static Logger logger = Logger.getLogger(ServiceMethods.class);

    public static Object getUniqueObjectJson(Class clazz, Serializable id) {
        Session session = null;
        Object object = null;
        try {
            session = HibernateFactory.getSession("smartshift");
            object = session.load(clazz, id);
            Hibernate.initialize(object);
        } catch(ObjectNotFoundException e) {
            String result = clazz.getCanonicalName() + " not found with the ID of " + id.toString();
            logger.error(result);
            throw APIResultFactory.getException(Status.BAD_REQUEST, result);
        } catch(Exception e) {
            logger.error("Failed to fetch " + clazz.getCanonicalName(), e);
            throw APIResultFactory.getException(Status.INTERNAL_SERVER_ERROR);
        } finally {
            logger.error("Closing session...");
            if(session != null)
                session.close();
        }
        return object;
    }

    public static List<Object> getObjectListJson(Class clazz) {
        Session session = null;
        List<Object> objects = null;
        try {
            session = HibernateFactory.getSession("smartshift");
            objects = session.createCriteria(clazz).list();
        } catch(Exception e) {
            logger.error("Failed to fetch the list of " + clazz.getCanonicalName(), e);
            throw APIResultFactory.getException(Status.INTERNAL_SERVER_ERROR);
        } finally {
            logger.error("Closing session...");
            if(session != null)
                session.close();
        }
        return objects;
    }
}
