package smartshift.common.hibernate;

/**
 * An enum for error that may occur when using the HibernateGenericUtil
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public enum DBError {
    /**
     * A unique object was not found
     */
    NotFound,
    /**
     * When CUD'ing an object, a reference was broken
     */
    ConstraintError,
    /**
     * When CU'ing, there wasn't good data
     */
    BadData
}
