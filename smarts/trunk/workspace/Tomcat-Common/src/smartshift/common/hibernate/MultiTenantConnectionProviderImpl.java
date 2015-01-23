package smartshift.common.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import smartshift.common.util.properties.AppConstants;

public class MultiTenantConnectionProviderImpl extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {
    private Map<String, C3P0ConnectionProvider> providers = new HashMap<>();
    
    private List<ServiceRegistryImplementor> serviceRegistries = new ArrayList<>();
    
    public MultiTenantConnectionProviderImpl() {
        Properties properties = getConnectionConfiguration(AppConstants.DB_SCHEMA_DEFAULT).getProperties();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(properties);
        injectServices((ServiceRegistryImplementor) ssrb.build());
    }

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

    @Override
    public ConnectionProvider getAnyConnectionProvider() {
        return selectConnectionProvider(AppConstants.DB_SCHEMA_DEFAULT);
    }

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

    public static Configuration getConnectionConfiguration(String schema) {
        Configuration cfg = new Configuration().configure();
        cfg.setProperty(Environment.URL, "jdbc:mysql://" + AppConstants.DB_SERVER_HOSTNAME + "/" + schema);
        return cfg;
    }
}
