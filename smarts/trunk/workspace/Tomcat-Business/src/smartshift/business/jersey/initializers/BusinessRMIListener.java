package smartshift.business.jersey.initializers;


import java.rmi.RemoteException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import smartshift.business.quartz.jobs.AccountsConnectionJob;
import smartshift.business.rmi.implementation.BusinessService;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.RMIServer;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;
import smartshift.common.util.properties.AppProperties;

/**
 * @author fevans
 * 
 *          the listener that starts the RMI service
 */
public class BusinessRMIListener implements ServletContextListener {  
    private static final SmartLogger logger = new SmartLogger(BusinessRMIListener.class);
    
    private JobDetail _jobDetail;
    
    private Trigger _trigger;
    
    private Scheduler _scheduler;
    
    /**
     * a context has been initialized, create the RMI service
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {   
        try {
            RMIServer.start(AppConstants.RMI_BUSINESS_PORT);
            RMIServer.create(BusinessService.class, AppConstants.RMI_BUSINESS_SERVICE_NAME);
        } catch(Exception e) {
            logger.error("Failed to create business RMI service", e);
        }
        
        try {
            _jobDetail = JobBuilder.newJob(AccountsConnectionJob.class)
                .withIdentity("accountsConnectionJob", "accountsConnectionGroup").build();
            
            _trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("accountsConnectionTrigger", "accountsConnectionGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(AppProperties.getIntegerProperty("rmi.connection.polling", 60)).repeatForever())
                    .build();
            
            _scheduler = new StdSchedulerFactory().getScheduler();
            _scheduler.scheduleJob(_jobDetail, _trigger);
            _scheduler.start();
        } catch(Exception e) {
            logger.error("Failed to start to accounts application connect job", e);
        }
    }  

    /**
     * a context has been destroyed, destroy the RMI service
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            if(_scheduler != null)
                _scheduler.shutdown();
        } catch(SchedulerException e) {
            logger.error("Failed to cancel Job!", e);
        }
        try {
            if(RMIClient.isClinetStarted(AppConstants.RMI_ACCOUNTS_HOSTNAME, AppConstants.RMI_ACCOUNTS_PORT)) {
                AccountsServiceInterface acctsService = (AccountsServiceInterface)
                        RMIClient.getService(AppConstants.RMI_ACCOUNTS_HOSTNAME, AppConstants.RMI_ACCOUNTS_PORT, AppConstants.RMI_ACCOUNTS_SERVICE_NAME);
                acctsService.businessDisconnecting(AppConstants.HOSTNAME, AppConstants.RMI_BUSINESS_PORT);
            }
        } catch(Exception e) {
            logger.error("Failed to warn accounts app of shutdown!", e);
        }
        try {
            RMIServer.destroyAll();
        } catch(RemoteException e) {
            logger.error("Failed to shut down RMI server!", e);
        }
    }
}
