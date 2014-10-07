package smartshift.common.hibernate;

import org.hibernate.proxy.HibernateProxy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * This factory will hand out the hibernate proxier for hibernate objects
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class HibernateProxyTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(HibernateProxy.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson);
        else
            return null;
    }
}
