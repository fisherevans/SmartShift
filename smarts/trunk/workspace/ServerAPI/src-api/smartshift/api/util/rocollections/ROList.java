package smartshift.api.util.rocollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of a read only list collection
 * 
 * @author dfead
 * 
 * @param <T>
 *            The type of the collected elements
 */
public class ROList<T> implements ROCollection<T> {
	
	private List<T> _list;

    /**
     * create a new ROList, wrapping the provided collection
     * 
     * @param copy
     *            the collection to wrap
     */
	public ROList (Collection<? extends T> copy) {
		initList();
		_list.addAll(copy);
	}

    /**
     * initialize the list
     */
    private void initList() {
        if (_list == null)
            _list = new ArrayList<T>();
    }
	
    /**
     * @see ROCollection#contains(Object)
     */
	@Override
	public boolean contains(T t) {
		return _list.contains(t);
	}

    /**
     * @see ROCollection#containsAll(Collection)
     */
	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return _list.containsAll(c);
	}

    /**
     * @see ROCollection#isEmpty()
     */
	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

    /**
     * @see ROCollection#iterator()
     */
	@Override
	public Iterator<T> iterator() {
		return _list.iterator();
	}

    /**
     * @see ROCollection#size()
     */
	@Override
	public int size() {
		return _list.size();
	}
	
    /**
     * @param index
     *            the index to check
     * @return the element at the provided index
     * @see java.util.List#get(int)
     */
	public T get(int index) {
		return _list.get(index);
	}
}
