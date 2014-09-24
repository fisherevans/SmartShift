package smartshift.api.util;

import java.util.Collection;
import java.util.Iterator;

public class ROSingleton<T> implements ROCollection<T>{
	
	
	
	private T _singleton;

	@Override
	public boolean contains(T t) {
		return t.equals(_singleton);
	}

	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return c.size()==1 && c.contains(_singleton);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new SingletonIterator<T>(_singleton);
	}

	@Override
	public int size() {
		return 1;
	}
}
