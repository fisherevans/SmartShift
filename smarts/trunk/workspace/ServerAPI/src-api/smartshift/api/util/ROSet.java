package smartshift.api.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ROSet<T> implements ROCollection<T>{

	public ROSet(Set<T> copy) {
		initSet();
		for(T t : copy) {
			_set.add(t);
		}
	}
	
	private void initSet() {
		if(_set == null)
			_set = new HashSet<T>();
	}
	
	private Set<T> _set;

	@Override
	public boolean contains(T t) {
		return _set.contains(t);
	}

	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return _set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return _set.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return _set.iterator();
	}

	@Override
	public int size() {
		return _set.size();
	}

}
