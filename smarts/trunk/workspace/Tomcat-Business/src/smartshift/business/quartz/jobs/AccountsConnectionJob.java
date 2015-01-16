package smartshift.business.quartz.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.RMIConnectionUtil;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 *  PEriodically attempts to connect to the accounts service
 */
public class AccountsConnectionJob implements Job {
    private static final Logger logger = Logger.getLogger(AccountsConnectionJob.class);
    private static boolean connectionInGoodStanding = false;

    /**
     * 
     */
    public AccountsConnectionJob() { }
    
    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            String host = AppConstants.RMI_ACCOUNTS_HOSTNAME;
            int port = AppConstants.RMI_ACCOUNTS_PORT;
            String serviceName = AppConstants.RMI_ACCOUNTS_SERVICE_NAME;
            if(RMIConnectionUtil.pollConnection(host, port, serviceName)) {
                if(!connectionInGoodStanding) {
                    connectionInGoodStanding = true;
                    logger.info("Connection is established");
                }
            } else {
                connectionInGoodStanding = false;
                RMIClient.stopClient(host, port);
            }
        } catch(Exception e) {
            logger.error("An unexpected error occured", e);
            connectionInGoodStanding = false;
            return;
        }
    }
}
