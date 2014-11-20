package smartshift.common.util.properties;

/**
 * Holds static properties based on App Properties
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class StaticProperties {
    /**
     * The schema for the Accounts Database
     */
    public static String DB_ACCOUNTS_SCHEMA;
    
    /**
     * Initialized static variables
     */
    public static void initialize() {
        DB_ACCOUNTS_SCHEMA = AppProperties.getProperty("database.schema.accounts");
    }
}
