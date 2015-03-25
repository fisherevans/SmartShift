package smartshift.common.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
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
    
    public static void unscheduleJob(JobKey key) {
        try {
            for(Scheduler scheduler:_schedFactory.getAllSchedulers()) {
                    scheduler.deleteJob(key);
            }
        } catch(SchedulerException e) {
            logger.fatal("Failed to stop job: " + key, e);
        }
    }
    
    public static JobKey createRepeatingJob(Class jobClass, String jobName, String jobGroup, String timeoutProperty, Integer defaultTimeout, JobDataMap jobData) {
        Integer timeout = AppProperties.getIntegerProperty(timeoutProperty, defaultTimeout);
        return QuartzHelper.createRepeatingJob(jobClass, jobName, jobGroup, timeout, jobData);
    }
    
    public static JobKey createRepeatingJob(Class jobClass, String jobName, String jobGroup, Integer timeout, JobDataMap jobData) {
        try {
            logger.info(String.format("Creating repeating job: %s - %s.%s - %d", jobClass, jobName, jobGroup, timeout));
            JobKey key = getJobKey(jobName + "Job", jobGroup);
            JobDetail jobDetail = JobBuilder
                    .newJob(jobClass)
                    .withIdentity(key)
                    .setJobData(jobData)
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobName + "Trigger", jobGroup)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(timeout).repeatForever())
                    .build();
            scheduleJob(jobDetail, trigger);
            return key;
        } catch(Exception e) {
            logger.error("Failed to start repeating job: " + jobName, e);
            return null;
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
    
    private static JobKey getJobKey(String name, String group) {
        return new JobKey(name + "Job", group);
    }
}
