package smartshift.business.jersey.initializers;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import smartshift.business.quartz.jobs.AccountsConnectionJob;
import smartshift.business.rmi.implementation.BusinessService;
import smartshift.common.rmi.RMIServer;
import smartshift.common.util.properties.AppConstants;
import smartshift.common.util.properties.AppProperties;

/**
 * @author fevans
 * 
 *          the listener that starts the RMI service
 */
public class BusinessRMIListener implements ServletContextListener {  
    private static final Logger logger = Logger.getLogger(BusinessRMIListener.class);
    
    /**
     * a context has been initialized, create the RMI service
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {   
        try {
            RMIServer.start(AppConstants.RMI_BUSINESS_PORT);
            RMIServer.create(BusinessService.class, AppConstants.RMI_BUSINESS_SERVICE_NAME);

            JobDetail job = JobBuilder.newJob(AccountsConnectionJob.class)
                .withIdentity("accountsConnectionJob", "accountsConnectionGroup").build();
            
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("accountsConnectionTrigger", "accountsConnectionGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(AppProperties.getIntegerProperty("rmi.connection.polling", 60)).repeatForever())
                    .build();
            
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch(Exception e) {
            logger.error("Failed to create business RMI service", e);
        }
    }  

    /**
     * a context has been destroyed, destroy the RMI service
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
