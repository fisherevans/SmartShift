package smartshift.api.util.rocollections;

import java.util.Collection;

/**
 * An interface used to encapslulate collections for read only use
 * 
 * @author dfead
 * 
 * @param <T>
 *            The type of the collected elements
 */
public interface ROCollection<T> extends Iterable<T> {

    /**
     * @param t
     *            the element to check for
     * @return true if the collection contains t
     * @see java.util.Collection#contains(Object)
     */
    public boolean contains(T t);

    /**
     * @param c
     *            the collection to check for
     * @return true if c is a subset of the collection
     * @see java.util.Collection#containsAll(Collection)
     */
    public boolean containsAll(Collection<? extends T> c);

    /**
     * @return true if the collection is empty
     * @see java.util.Collection#isEmpty()
     */
    public boolean isEmpty();

    /**
     * @return the number of elements in the collection
     * @see java.util.Collection#size()
     */
    public int size();
}
