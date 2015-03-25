package smartshift.common.hibernate;

import org.hibernate.Session;

/**
 * @author fevans
 * a context for a dao
 */
public interface DAOContext {
   /**
    * @return the hibernate session for this context
    */
   public Session getSession();
   
   /**
     * @return the id of this context - used to compare other DAO contexts with .equals
     */
public Object getContextID();
}
