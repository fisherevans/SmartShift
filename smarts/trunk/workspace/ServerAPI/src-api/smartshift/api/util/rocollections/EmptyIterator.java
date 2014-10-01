package smartshift.api.util.rocollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator with an empty collection
 * 
 * @author dfead
 *
 * @param <T>
 */
public class EmptyIterator<T> implements Iterator<T> {

    /**
     * Create the empty iterator
     */
	public EmptyIterator() {
		// do nothing
	}
	
    /**
     * @return always false
     */
	@Override
	public boolean hasNext() {
		return false;
	}

    /**
     * @throws NoSuchElementException
     *             always
     */
	@Override
	public T next() {
		throw new NoSuchElementException();
	}

    /**
     * @throws UnsupportedOperationException
     *             always
     */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}