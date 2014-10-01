package smartshift.api.util;

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
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
