package smartshift.api.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator that goes over a single object
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

	@Override
	public boolean hasNext() {
		return !_used;
	}

	@Override
	public T next() {
		if(_used)
			throw new NoSuchElementException();
		_used = true;
		return _singleton;
	}

	@Override
	public void remove() {
        if(_used)
            throw new NoSuchElementException();
        else
            _used = true;
	}
	
}
