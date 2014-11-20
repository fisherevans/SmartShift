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
 * This TypeAdapter will initialize Hibernate proxied objects
 * 
 * See http://stackoverflow.com/questions/13459718/could-not-serialize-object-cause-of-hibernateproxy
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>, Flavio
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

    /**
     * The factory for this adapter
     */
    public static final HibernateProxyTypeAdapterFactory FACTORY = new HibernateProxyTypeAdapterFactory();

    private final Gson context;

    /**
     * Generate the adapter
     * @param context the gson context to base it on
     */
    public HibernateProxyTypeAdapter(Gson context) {
        this.context = context;
    }

    /**
     * read an object
     */
    @Override
    public HibernateProxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * write a json object, intiializing any hibernate objects
     */
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