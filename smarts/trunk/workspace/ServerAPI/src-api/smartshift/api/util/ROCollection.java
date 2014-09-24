package smartshift.api.util;

import java.util.Collection;

public interface ROCollection<T> extends Iterable<T> {


	public boolean contains(T t);

	public boolean containsAll(Collection<? extends T> c);

	public boolean isEmpty();

	public int size();
}
