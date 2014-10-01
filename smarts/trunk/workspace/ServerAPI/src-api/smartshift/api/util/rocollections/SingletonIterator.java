package smartshift.api.util.rocollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator wrapping a single object
 * 
 * @param <T>
 *            the type of the object to wrap
 */
public class SingletonIterator<T> implements Iterator<T> {

    private boolean _used;

    private T _singleton;

    /**
     * Create the iterator
     * 
     * @param singleton the only object to iterate over
     */
	public SingletonIterator(T singleton) {
		_singleton = singleton;
        _used = false;
	}

    /**
     * @return true if the object hasn't been used
     */
	@Override
	public boolean hasNext() {
		return !_used;
	}

    /**
     * @return the object
     * @throws NoSuchElementException
     *             if the object has been used already
     */
	@Override
	public T next() {
		if(_used)
			throw new NoSuchElementException();
		_used = true;
		return _singleton;
	}

    /**
     * remove the element from iteration
     * 
     * @throws NoSuchElementException
     *             if the object has been used already
     */
	@Override
	public void remove() {
        if(_used)
            throw new NoSuchElementException();
        else
            _used = true;
	}
	
}
