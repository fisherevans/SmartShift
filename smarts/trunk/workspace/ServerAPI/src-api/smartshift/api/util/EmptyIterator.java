package smartshift.api.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator<T> implements Iterator<T> {

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
