package smartshift.common.util.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An implementation of a read only set collection
 * 
 * @author dfead
 * 
 * @param <T>
 *            The type of the collected elements
 */
public class ROSet<T> implements ROCollection<T>{
    
    private Set<T> _set;

    /**
     * Create the read only set, wrapping a set
     * 
     * @param copy
     *            the set to wrap
     */
	public ROSet(Set<T> copy) {
		initSet();
		for(T t : copy) {
			_set.add(t);
		}
	}
	
    /**
     * initialize the set
     */
	private void initSet() {
		if(_set == null)
			_set = new HashSet<T>();
	}

    /**
     * @see ROCollection#contains(Object)
     */
	@Override
	public boolean contains(T t) {
		return _set.contains(t);
	}

    /**
     * @see ROCollection#containsAll(Collection)
     */
	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return _set.containsAll(c);
	}

    /**
     * @see ROCollection#isEmpty()
     */
	@Override
	public boolean isEmpty() {
		return _set.isEmpty();
	}

    /**
     * @see ROCollection#iterator()
     */
	@Override
	public Iterator<T> iterator() {
		return _set.iterator();
	}

    /**
     * @see ROCollection#size()
     */
	@Override
	public int size() {
		return _set.size();
	}

}
