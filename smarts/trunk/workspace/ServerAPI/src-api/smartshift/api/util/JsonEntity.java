package smartshift.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Parent class that sets up the GSON_BUILDER for table entities. 
 * Allows entities to configure it's builder via the static {} 
 * clause to fit their needs. Adds functionality to get the 
 * GSON and JSON text.
 * @author fevans
 */
public abstract class JsonEntity {
	private static final GsonBuilder GSON_BUILDER;

	private static final Boolean USE_PRETTY_PRINT = true;
	
	private static final Boolean REQUIRE_EXPOSE = true;
	
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SS'Z'";
	
	static {
		GSON_BUILDER = new GsonBuilder();
		GSON_BUILDER.setDateFormat(DATE_FORMAT);
		if(USE_PRETTY_PRINT)
			GSON_BUILDER.setPrettyPrinting();
		if(REQUIRE_EXPOSE)
			GSON_BUILDER.excludeFieldsWithoutExposeAnnotation();
	}
	
	/**
	 * Protected class to get the GSON builder for changed its settings
	 * @return the GSON Builder
	 */
	protected static GsonBuilder getGsonBuilder() {
		return GSON_BUILDER;
	}
	
	/**
	 * The GSON object based on this object's GSON Builder
	 * @return
	 */
	public Gson getGson() {
		return GSON_BUILDER.create();
	}
	
	/**
	 * Uses this object's GSON Builder to generate the JSON text object
	 * @return the JSON
	 */
	public String toJson() {
		return getGson().toJson(this);
	}
}
