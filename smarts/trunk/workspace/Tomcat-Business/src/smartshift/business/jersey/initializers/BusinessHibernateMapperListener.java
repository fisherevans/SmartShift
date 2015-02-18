package smartshift.business.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.GroupRoleEmployeeModel;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.hibernate.HibernateFactory;

/**
 * Business side, adds the proper classes to the hibernate config
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class BusinessHibernateMapperListener implements ServletContextListener {

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateFactory.addAnnotatedClass(EmployeeModel.class);
        HibernateFactory.addAnnotatedClass(GroupModel.class);
        HibernateFactory.addAnnotatedClass(GroupEmployeeModel.class);
        HibernateFactory.addAnnotatedClass(GroupRoleModel.class);
        HibernateFactory.addAnnotatedClass(RoleModel.class);
        HibernateFactory.addAnnotatedClass(GroupRoleEmployeeModel.class);
        
        HibernateFactory.addAnnotatedClass(AvailabilityInstanceModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityTemplateModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityRepeatWeeklyModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityRepeatMonthlyByDayModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityRepeatMonthlyByDateModel.class);
        HibernateFactory.addAnnotatedClass(AvailabilityRepeatYearlyModel.class);
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
