package smartshift.common.util.collections;

/**
 * @author dfead
 * @version Oct 31, 2014
 * 
 * @param <T> the type of object to filter on
 * 
 * an interface for filtering elements of a collection
 */
public interface Filter<T> {

    /**
     * determine whether the provided element should pass through the filter
     * 
     * @param element
     * @return false to filter out the element
     */
    public boolean include(T element);
}
