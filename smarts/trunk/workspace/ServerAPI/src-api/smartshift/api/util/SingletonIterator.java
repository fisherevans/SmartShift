package smartshift.api.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingletonIterator<T> implements Iterator<T> {

	public SingletonIterator(T singleton) {
		_singleton = singleton;
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
		throw new NoSuchElementException();
	}

	private boolean _used;
	private T _singleton;
	
}
