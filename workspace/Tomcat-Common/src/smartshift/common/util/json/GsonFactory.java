package smartshift.common.util.json;

import javax.ws.rs.core.Response.Status;
import smartshift.common.hibernate.HibernateProxyTypeAdapter;
import smartshift.common.util.properties.AppProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This factory dictates how the json coming in and going out are formatted and parsed
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GsonFactory {
    /**
     * The builder that creates the base GSON reader
     */
    private static GsonBuilder gsonBuilder = null;

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SS'Z'";

    /**
     * Initialize the factory. Requires App Properties to be initialized first
     */
    public static void initialize() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        if(AppProperties.getProperty("gson.prettyPrint").equalsIgnoreCase("true"))
            gsonBuilder.setPrettyPrinting();
    }

    /**
     * Protected class to get the GSON builder for changed its settings
     * 
     * @return the GSON Builder
     */
    protected static GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    /**
     * @return the GSON object based on this object's GSON Builder
     */
    public static Gson getGson() {
        return gsonBuilder.create();
    }

    /**
     * Uses this object's GSON Builder to generate the JSON text object
     * 
     * @param object The object to turn into json
     * @return the JSON
     */
    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * Uses this object's GSON Builder to generate the JSON encapsulated by the
     * standard JsonResult object
     * 
     * @param object The object to turn into json
     * @return the JSON
     */
    public static String toJsonResult(Object object) {
        return getGson().toJson(new JsonResult(object));
    }

    /**
     * Uses this object's GSON Builder to generate the JSON encapsulated by the
     * standard JsonResult object
     * 
     * @param status The HTTP status of the result
     * @param object The object to turn into json
     * @return the JSON
     */
    public static String toJsonResult(Status status, Object object) {
        return getGson().toJson(new JsonResult(status, object));
    }
}
