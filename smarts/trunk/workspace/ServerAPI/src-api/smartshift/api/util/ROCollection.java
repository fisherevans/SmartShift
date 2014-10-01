package smartshift.api.util;

import java.util.Collection;

/**
 * An interface used to encapslulate collections for read only use
 * 
 * @author dfead
 *
 * @param <T>
 *            The type of the collection
 */
public interface ROCollection<T> extends Iterable<T> {
    /**
     * @see java.util.Collection#contains()
     */
	public boolean contains(T t);

    /**
     * @see java.util.Collection#containsAll()
     */
	public boolean containsAll(Collection<? extends T> c);

    /**
     * @see java.util.Collection#isEmpty()
     */
	public boolean isEmpty();

    /**
     * @see java.util.Collection#size()
     */
	public int size();
}
