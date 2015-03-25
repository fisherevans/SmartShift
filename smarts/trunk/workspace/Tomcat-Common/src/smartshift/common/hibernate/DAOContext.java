package smartshift.common.hibernate;

import org.hibernate.Session;

public interface DAOContext {
   /**
    * @return the hibernate session for this context
    */
   public Session getSession();
   
   public Object getContextID();
}
