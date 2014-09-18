package smartshift.api.hibernate;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class HibernateListener implements ServletContextListener {  

	@Override
    public void contextInitialized(ServletContextEvent event) {  
        HibernateUtil.getSessionFactory(); // Just call the static initializer of that class      
    }  

	@Override
    public void contextDestroyed(ServletContextEvent event) {  
        HibernateUtil.getSessionFactory().close(); // Free all resources  
    }
	
}