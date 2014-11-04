package smartshift.common.util.collections;

import java.util.Collection;

/**
 * An interface used to encapsulate collections for read only use
 * 
 * @author dfead
 * 
 * @param <T> The type of the collected elements
 */
public abstract class ROCollection<T> implements Iterable<T> {

    /**
     * @param t
     *            the element to check for
     * @return true if the collection contains t
     * @see java.util.Collection#contains(Object)
     */
    public abstract boolean contains(T t);

    /**
     * @param c
     *            the collection to check for
     * @return true if c is a subset of the collection
     * @see java.util.Collection#containsAll(Collection)
     */
    public abstract boolean containsAll(Collection<? extends T> c);

    /**
     * @return true if the collection is empty
     * @see java.util.Collection#isEmpty()
     */
    public abstract boolean isEmpty();

    /**
     * @return the number of elements in the collection
     * @see java.util.Collection#size()
     */
    public abstract int size();

    /**
     * @param <S>
     * @param <T>
     * @param collected
     * @return a ROCollection wrapper for any list
     */
    public static <S, T extends Collection<S>> ROCollection<S> wrap(T collected) {
        return new ROList<S>(collected);
    }
}
