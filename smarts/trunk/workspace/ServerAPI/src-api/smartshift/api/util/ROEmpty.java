package smartshift.api.util;

import java.util.Collection;
import java.util.Iterator;

public class ROEmpty<T> implements ROCollection<T> {

	@Override
	public boolean contains(T t) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return new EmptyIterator<T>();
	}

	@Override
	public int size() {
		return 0;
	}
}
