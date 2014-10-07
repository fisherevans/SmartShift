package smartshift.common.util.json;

import smartshift.common.hibernate.HibernateProxyTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    private static final GsonBuilder GSON_BUILDER;

    private static final Boolean USE_PRETTY_PRINT = true;

    private static final Boolean REQUIRE_EXPOSE = true;

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SS'Z'";

    static {
        GSON_BUILDER = new GsonBuilder();
        GSON_BUILDER.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        GSON_BUILDER.setDateFormat(DATE_FORMAT);
        if(USE_PRETTY_PRINT)
            GSON_BUILDER.setPrettyPrinting();
        if(REQUIRE_EXPOSE)
            GSON_BUILDER.excludeFieldsWithoutExposeAnnotation();
    }

    /**
     * Protected class to get the GSON builder for changed its settings
     * 
     * @return the GSON Builder
     */
    protected static GsonBuilder getGsonBuilder() {
        return GSON_BUILDER;
    }

    /**
     * @return the GSON object based on this object's GSON Builder
     */
    public static Gson getGson() {
        return GSON_BUILDER.create();
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
}
