package smartshift.common.hibernate;

import java.io.IOException;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * This TypeAdapter will intitialize Hibernate proxied objects
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

    public static final HibernateProxyTypeAdapterFactory FACTORY = new HibernateProxyTypeAdapterFactory();

    private final Gson context;

    public HibernateProxyTypeAdapter(Gson context) {
        this.context = context;
    }

    @Override
    public HibernateProxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void write(JsonWriter out, HibernateProxy value) throws IOException {
        if(value == null) {
            out.nullValue();
            return;
        }
        // Retrieve the original (not proxy) class
        Class<?> baseType = Hibernate.getClass(value);
        // Get the TypeAdapter of the original class, to delegate the
        // serialization
        TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));
        // Get a filled instance of the original class
        Object unproxiedValue = value.getHibernateLazyInitializer().getImplementation();
        // Serialize the value
        delegate.write(out, unproxiedValue);
    }
}