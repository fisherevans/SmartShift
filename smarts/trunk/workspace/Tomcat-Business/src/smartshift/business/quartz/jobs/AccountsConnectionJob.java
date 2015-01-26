package smartshift.business.quartz.jobs;

import java.rmi.RemoteException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.RMIConnectionUtil;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 *  PEriodically attempts to connect to the accounts service
 */
public class AccountsConnectionJob implements Job {
    private static final SmartLogger logger = new SmartLogger(AccountsConnectionJob.class);
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
            if(!RMIConnectionUtil.pollConnection(host, port, serviceName)) {
                connectionInGoodStanding = false;
                RMIClient.stopClient(host, port);
                return;
            }

            if(!connectionInGoodStanding) {
                try {
                    AccountsServiceInterface as = (AccountsServiceInterface) RMIClient.getService(host, port, serviceName);
                    as.businessConnected(AppConstants.HOSTNAME, AppConstants.RMI_BUSINESS_PORT, AppConstants.DEV_BUSINESS_MANUAL_BUSINESSES);
                } catch(RemoteException e) {
                    logger.error("Failed to notify accounts application.", e);
                    return;
                }
                connectionInGoodStanding = true;
                logger.info("Connection is established");
            }
        } catch(Exception e) {
            logger.error("An unexpected error occured", e);
            connectionInGoodStanding = false;
            return;
        }
    }
}
