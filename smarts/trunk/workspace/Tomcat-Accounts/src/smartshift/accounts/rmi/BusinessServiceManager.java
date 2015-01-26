package smartshift.accounts.rmi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.collections.ROSet;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * Manager for business application RMI connections
 */
public class BusinessServiceManager {
    private static final Logger logger = Logger.getLogger(BusinessServiceManager.class);
    
    private static Map<Integer, Set<BusinessServiceInterface>> businessServices = new HashMap<>();
    
    private static Map<String, BusinessServiceInterface> hostServices = new HashMap<>();
    
    private static Map<BusinessServiceInterface, String> serviceHosts = new HashMap<>();
    
    /** Add a service
     * @param businessService the business service
     * @param hostname the service's hostname
     * @param businessIDs the business ids to add this service to
     */
    public static void addService(BusinessServiceInterface businessService, String hostname, Integer ... businessIDs) {
        logger.info("Registering " + hostname + " for the following businesses: " + PrimativeUtils.joinArray(businessIDs, " "));
        hostServices.put(hostname, businessService);
        serviceHosts.put(businessService, hostname);
        for(Integer businessID:businessIDs)
            getBusinessServiceSet(businessID).add(businessService);
    }
    
    /** remove a service for this hostname
     * @param hostname the hostname to rmeove
     */
    public static void removeService(String hostname) {
        logger.info("Removing Service: " + hostname);
        BusinessServiceInterface deleteService = hostServices.remove(hostname);
        if(deleteService != null)
            serviceHosts.remove(deleteService);
        Iterator<Entry<Integer, Set<BusinessServiceInterface>>> businessServicesIterator = businessServices.entrySet().iterator();
        while(businessServicesIterator.hasNext()) {
            Entry<Integer, Set<BusinessServiceInterface>> businesServiceSetEntry = businessServicesIterator.next();
            Iterator<BusinessServiceInterface> serviceSetIterator = businesServiceSetEntry.getValue().iterator();
            while(serviceSetIterator.hasNext()) {
                if(serviceSetIterator.next() == deleteService)
                    serviceSetIterator.remove();
            }
        }
    }
    
    /** get the service for a hostname
     * @param hostname the hostname to lookuo
     * @return the service
     */
    public static BusinessServiceInterface getHostService(String hostname) {
        return hostServices.get(hostname);
    }
    
    /** get all services for a business id
     * @param businessID the business id
     * @return the ro set of services
     */
    public static ROSet<BusinessServiceInterface> getBusinessServices(Integer businessID) {
        return new ROSet<BusinessServiceInterface>(getBusinessServiceSet(businessID));
    }
    
    /** get all registered business ids
     * @return the or set of business ids
     */
    public static ROSet<Integer> getAllBusinessIds() {
        Set<Integer> businessIDs = new HashSet<>();
        for(Integer businessID:businessServices.keySet()) {
            if(businessServices.get(businessID).size() > 0)
                businessIDs.add(businessID);
        }
        return new ROSet<Integer>(businessIDs);
    }
    
    /** get all registered hostnames
     * @return the set of hostnames
     */
    public static ROSet<String> getAllHostnames() {
        return new ROSet<String>(hostServices.keySet());
    }
    
    /** internal use only - get the set - if it doesn't exist, create it and store it
     * @param businessID the business id
     * @return the existing or new set
     */
    private static Set<BusinessServiceInterface> getBusinessServiceSet(Integer businessID) {
        Set<BusinessServiceInterface> businessServiceSet = businessServices.get(businessID);
        if(businessServiceSet == null) {
            businessServiceSet = new LinkedHashSet<>();
            businessServices.put(businessID, businessServiceSet);
        }
        return businessServiceSet;
    }
    
    /** check all services, attempt to ping. If it fails, remove it
     */
    public static void cleanAllConnections() {
        for(Integer businessID:businessServices.keySet())
            cleanConnections(businessID);
    }
    
    /** check all services associated with this businessid, attempt to ping. If it failes, remove it
     * @param businessID the business id
     */
    public static void cleanConnections(Integer businessID) {
        List<String> deadHosts = new LinkedList<>();
        for(BusinessServiceInterface businessService:getBusinessServiceSet(businessID)) {
            String hostname = serviceHosts.get(businessService);
            try {
                if(!businessService.ping().equalsIgnoreCase("pong")) {
                    logger.error(String.format("Ping was not pong!!!: %s %d", hostname, businessID));
                    deadHosts.add(hostname);
                }
            } catch(Exception e) {
                logger.error(String.format("Failed to ping business service: %s %d", hostname, businessID));
                deadHosts.add(hostname);
            }
        }
        for(String deadHost:deadHosts)
            removeService(deadHost);
    }

    /**
     * Disconnects all business services and clears the this registry
     * @param invalidateSessions pass true if you want to invalidate all user sessions at the same time
     */
    public static void closeAllConnections(boolean invalidateSessions) {
        for(Integer businessID:businessServices.keySet()) {
            for(BusinessServiceInterface service:businessServices.get(businessID)) {
                String host = serviceHosts.get(service);
                try {
                    if(invalidateSessions)
                        service.invalidateAllUserSessions(null);
                    service.disconnecting();
                } catch(Exception e) {
                    logger.error("Failed to warn " + host + " of closing");
                }
            }
        }
        businessServices.clear();
        hostServices.clear();
        serviceHosts.clear();
    }
}
