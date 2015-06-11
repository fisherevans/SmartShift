package smartshift.common.util.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author drew
 *
 * @param <T>
 */
public class ROFilteredCollection<T> extends ROCollection<T> {

    private Filter<T> _filter;
    private ROCollection<T> _collected;

    /**
     * constructor for a new read only filtered collection
     * 
     * @param filter
     * @param collected
     */
    public ROFilteredCollection(Filter<T> filter, ROCollection<T> collected) {
        _filter = filter;
        _collected = collected;
    }

    /**
     * @see ROCollection#contains(Object)
     */
    @Override
    public boolean isEmpty() {
        if(_collected.isEmpty())
            return true;
        for(T element : _collected) {
            if(_filter.include(element))
                return false;
        }
        return true;
    }

    /**
     * @return an iterator yielding the elements
     * @see ROCollection#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return new ROIterator<T>(new FilteredIterator<T>(_filter, _collected.iterator()));
    }

    /**
     * @see ROCollection#size()
     */
    @Override
    public int size() {
        int i = 0;
        for(T element : _collected) {
            if(_filter.include(element))
                i++;
        }
        return i;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return _collected.toString() + " filtered by " + _filter.toString();
    }

    /**
     * @see ROCollection#containsAll(Collection)
     */
    @Override
    public boolean containsAll(Collection<? extends T> c) {
        boolean all = true;
        for(T element : c) {
            if(!(_filter.include(element) && _collected.contains(element)))
                all = false;
        }
        return all;
    }

    /**
     * @see ROCollection#contains(Object)
     */
    @Override
    public boolean contains(T element) {
        return _filter.include(element) && _collected.contains(element);
    }
}
