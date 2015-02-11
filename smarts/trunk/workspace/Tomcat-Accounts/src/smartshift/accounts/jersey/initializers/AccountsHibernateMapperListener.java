package smartshift.accounts.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.hibernate.HibernateFactory;

/**
 * Accounts side, adds the proper classes to the hibernate config
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class AccountsHibernateMapperListener implements ServletContextListener {

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateFactory.addAnnotatedClass(UserModel.class);
        HibernateFactory.addAnnotatedClass(SessionModel.class);
        HibernateFactory.addAnnotatedClass(UserBusinessEmployeeModel.class);
        HibernateFactory.addAnnotatedClass(BusinessModel.class);
        HibernateFactory.addAnnotatedClass(SessionModel.class);
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
