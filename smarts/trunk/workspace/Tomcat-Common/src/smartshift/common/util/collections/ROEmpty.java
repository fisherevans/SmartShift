package smartshift.common.util.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * An implementation of an empty read only collection
 * 
 * @author dfead
 * 
 * @param <T>
 *            The type of the collected elements
 */
public class ROEmpty<T> extends ROCollection<T> {

    /**
     * @return always false
     */
	@Override
	public boolean contains(T t) {
		return false;
	}

    /**
     * @return always false
     */
	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return false;
	}

    /**
     * @return always true
     */
	@Override
	public boolean isEmpty() {
		return true;
	}

    /**
     * @return an empty iterator
     */
	@Override
	public Iterator<T> iterator() {
		return new EmptyIterator<T>();
	}

    /**
     * @return always 0
     */
	@Override
	public int size() {
		return 0;
	}
}