package smartshift.accounts.rmi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.collections.ROSet;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * Manager for business application RMI connections
 */
public class BusinessServiceManager {
    private static final SmartLogger logger = new SmartLogger(BusinessServiceManager.class);
    
    /** main map for business id -> services to call for that business */
    private static Map<Integer, Set<BusinessServiceInterface>> businessServices = new HashMap<>();
    
    /** lookup map to find what service a host offers */
    private static Map<String, BusinessServiceInterface> hostServices = new HashMap<>();
    
    /** lookup map to find what host a service belongs to */
    private static Map<BusinessServiceInterface, String> serviceHosts = new HashMap<>();
    
    /** Add a service
     * @param businessService the business service
     * @param hostname the service's hostname
     * @param businessIDs the business ids to add this service to
     */
    public synchronized static void addService(BusinessServiceInterface businessService, String hostname, Integer ... businessIDs) {
        logger.info("Registering " + hostname + " for the following businesses: " + PrimativeUtils.joinArray(businessIDs, " "));
        hostServices.put(hostname, businessService);
        serviceHosts.put(businessService, hostname);
        for(Integer businessID:businessIDs)
            getBusinessServiceSet(businessID).add(businessService);
    }
    
    /** remove a service for this hostname
     * @param hostname the hostname to rmeove
     */
    public synchronized static void removeService(String hostname) {
        logger.info("Removing Service: " + hostname);
        BusinessServiceInterface deleteService = hostServices.remove(hostname);
        if(deleteService != null)
            serviceHosts.remove(deleteService);
        for(Set<BusinessServiceInterface> businessServiceSet:businessServices.values()) {
            Iterator<BusinessServiceInterface> serviceSetIterator = businessServiceSet.iterator();
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
    public synchronized static BusinessServiceInterface getHostService(String hostname) {
        return hostServices.get(hostname);
    }
    
    /** get all services for a business id
     * @param businessID the business id
     * @return the ro set of services
     */
    public synchronized static ROSet<BusinessServiceInterface> getBusinessServices(Integer businessID) {
        return new ROSet<BusinessServiceInterface>(getBusinessServiceSet(businessID));
    }
    
    /** get all registered business ids
     * @return the or set of business ids
     */
    public synchronized static ROSet<Integer> getAllBusinessIds() {
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
    public synchronized static ROSet<String> getAllHostnames() {
        return new ROSet<String>(hostServices.keySet());
    }
    
    /** internal use only - get the set - if it doesn't exist, create it and store it
     * @param businessID the business id
     * @return the existing or new set
     */
    private synchronized static Set<BusinessServiceInterface> getBusinessServiceSet(Integer businessID) {
        Set<BusinessServiceInterface> businessServiceSet = businessServices.get(businessID);
        if(businessServiceSet == null) {
            businessServiceSet = new LinkedHashSet<>();
            businessServices.put(businessID, businessServiceSet);
        }
        return businessServiceSet;
    }
    
    /** check all services, attempt to ping. If it fails, remove it
     */
    public synchronized static void cleanAllConnections() {
        for(Integer businessID:businessServices.keySet())
            cleanConnections(businessID);
    }
    
    /** check all services associated with this businessid, attempt to ping. If it failes, remove it
     * @param businessID the business id
     */
    public synchronized static void cleanConnections(Integer businessID) {
        Set<String> deadHosts = new HashSet<>();
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
    public synchronized static void closeAllConnections(boolean invalidateSessions) {
        for(Integer businessID:businessServices.keySet()) {
            for(BusinessServiceInterface service:businessServices.get(businessID)) {
                String host = serviceHosts.get(service);
                try {
                    if(invalidateSessions)
                        service.invalidateAllUserSessions(null);
                    service.accountsDisconnecting();
                } catch(Exception e) {
                    logger.error("Failed to warn " + host + " of closing", e);
                }
            }
        }
        businessServices.clear();
        hostServices.clear();
        serviceHosts.clear();
    }
}
