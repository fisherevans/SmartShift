package smartshift.common.util.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * An implementation of a read only collection containing one element
 * 
 * @author dfead
 * 
 * @param <T>
 *            The type of the collected element
 */
public class ROSingleton<T> extends ROCollection<T> {
	
    private T _singleton;
	
    /**
     * Create the read only singleton
     * @param singleton The only object to be conetained in the RO collection
     */
    public ROSingleton(T singleton) {
        _singleton = singleton;
    }

    /**
     * @see ROCollection#contains(Object)
     */
	@Override
	public boolean contains(T t) {
		return t.equals(_singleton);
	}

    /**
     * @see ROCollection#containsAll(Collection)
     */
	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return c.size()==1 && c.contains(_singleton);
	}

    /**
     * @see ROCollection#isEmpty()
     */
	@Override
	public boolean isEmpty() {
		return false;
	}

    /**
     * @see ROCollection#iterator()
     */
	@Override
	public Iterator<T> iterator() {
		return new SingletonIterator<T>(_singleton);
	}

    /**
     * @return always 1
     * @see ROCollection#size()
     */
	@Override
	public int size() {
		return 1;
	}
}
