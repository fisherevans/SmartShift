package smartshift.common.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * Redirects schema connection requests for hibernate
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class MultiTenantConnectionProviderImpl extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {
    private static final SmartLogger logger = new SmartLogger(MultiTenantConnectionProviderImpl.class);
    
    private static final long serialVersionUID = -8842086563564584371L;

    private Map<String, C3P0ConnectionProvider> providers = new HashMap<>();
    
    private List<ServiceRegistryImplementor> serviceRegistries = new ArrayList<>();
    
    private static MultiTenantConnectionProviderImpl instance;
    
    /**
     * Initializes this provider - registers the standard service registry
     */
    public MultiTenantConnectionProviderImpl() {
        Properties properties = getConnectionConfiguration(AppConstants.DB_SCHEMA_DEFAULT).getProperties();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(properties);
        injectServices((ServiceRegistryImplementor) ssrb.build());
        instance = this;
    }

    /**
     * @see org.hibernate.service.spi.ServiceRegistryAwareService#injectServices(org.hibernate.service.spi.ServiceRegistryImplementor)
     */
    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        C3P0ConnectionProvider provider;
        if(!serviceRegistries.contains(serviceRegistry)) {
            for(String schema:providers.keySet()) {
                provider = providers.get(schema);
                if(provider != null) {
                    provider.injectServices(serviceRegistry);
                }
            }
        }
        serviceRegistries.add(serviceRegistry);
    }

    /**
     * @see org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider#getAnyConnectionProvider()
     */
    @Override
    public ConnectionProvider getAnyConnectionProvider() {
        return selectConnectionProvider(AppConstants.DB_SCHEMA_DEFAULT);
    }

    /**
     * @see org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider#selectConnectionProvider(java.lang.String)
     */
    @Override
    public ConnectionProvider selectConnectionProvider(String schema) {
        C3P0ConnectionProvider provider = providers.get(schema);
        if(provider == null) {
            provider = new C3P0ConnectionProvider();
            for(ServiceRegistryImplementor serviceRegistry:serviceRegistries)
                provider.injectServices(serviceRegistry);
            provider.configure(getConnectionConfiguration(schema).getProperties());
            providers.put(schema, provider);
        }
        return provider;
    }

    /**
     * Given a schema, return the hibernate configuration to connect to it
     * @param schema the schema to connect to
     * @return the base hibernate config with the url set to the schema path
     */
    public static Configuration getConnectionConfiguration(String schema) {
        Configuration cfg = new Configuration().configure();
        cfg.setProperty(Environment.URL, "jdbc:mysql://" + AppConstants.DB_SERVER_HOSTNAME + "/" + schema);
        return cfg;
    }
    
    /**
     * Closes all provider connection (when the app comes down)
     */
    public void close() {
        try {
            for(C3P0ConnectionProvider provider: providers.values()) {
                try {
                    provider.stop();
                } catch(Exception e) {
                    logger.error("Failed to close provider", e);
                }
            }
            providers.clear();
        } catch(Exception e) {
            logger.error("Failed to close providers", e);
        }
    }

    /** gets an instance of this class
     * @return the instance
     */
    public static MultiTenantConnectionProviderImpl getInstance() {
        return instance;
    }
    
    
}
