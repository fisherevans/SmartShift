package smartshift.common.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppProperties;

public class QuartzHelper {
    private static final SmartLogger logger = new SmartLogger(QuartzHelper.class);
    
    private static StdSchedulerFactory _schedFactory = null;
    
    public static void createRepeatingJob(Class jobClass, String jobName, String jobGroup, String timeoutProperty, Integer defaultTimeout) {
        Integer timeout = AppProperties.getIntegerProperty(timeoutProperty, defaultTimeout);
        QuartzHelper.createRepeatingJob(jobClass, jobName, jobGroup, timeout);
    }
    
    public static void createRepeatingJob(Class jobClass, String jobName, String jobGroup, Integer timeout) {
        try {
            logger.info(String.format("Creating repeating job: %s - %s.%s - %d", jobClass, jobName, jobGroup, timeout));
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName + "Job", jobGroup).build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobName + "Trigger", jobGroup)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(timeout).repeatForever())
                    .build();
            scheduleJob(jobDetail, trigger);
        } catch(Exception e) {
            logger.error("Failed to start repeating job: " + jobName, e);
        }
    }
    
    
    public static void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
    
    public static StdSchedulerFactory getFactory() {
        if(_schedFactory == null)
            _schedFactory = new StdSchedulerFactory();
        return _schedFactory;
    }
    
    public static Scheduler getScheduler() {
        try {
            Scheduler scheduler = getFactory().getScheduler();
            return scheduler;
        } catch(Exception e) {
            logger.error("Failed to create scheduler!", e);
            return null;
        }
    }
    
    public static void stopAllJobs() {
        if(_schedFactory == null) {
            logger.warn("Scheduler was null - no way to stop jobs.");
            return;
        }
        try {
            for(Scheduler sched:getFactory().getAllSchedulers()) {
                logger.info("Shutting down Quartz scheduler... " + sched);
                sched.shutdown(true);
                logger.debug("The Quartz scheduler has been shut down.");
            }
        } catch(SchedulerException e) {
            logger.error("Failed to cancel Job!", e);
        }
    }
}
