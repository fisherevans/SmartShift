package smartshift.api.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * An implementation of a read only collection containing one element
 * 
 * @author dfead
 *
 * @param <T> The type of the collection
 */
public class ROSingleton<T> implements ROCollection<T>{
	
    private T _singleton;
	
    /**
     * Create the read only singleton
     * @param singleton The only object to be conetained in the RO collection
     */
    public ROSingleton(T singleton) {
        _singleton = singleton;
    }

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
