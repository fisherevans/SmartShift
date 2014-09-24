package smartshift.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ROList<T> implements ROCollection<T> {
	
	private List<T> _list;

	public ROList (Collection<? extends T> copy) {
		initList();
		_list.addAll(copy);
	}
	
	@Override
	public boolean contains(T t) {
		return _list.contains(t);
	}

	@Override
	public boolean containsAll(Collection<? extends T> c) {
		return _list.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return _list.iterator();
	}

	@Override
	public int size() {
		return _list.size();
	}
	
	public T get(int index) {
		return _list.get(index);
	}
	
	private void initList() {
		if(_list == null)
			_list = new ArrayList<T>();
	}
}
