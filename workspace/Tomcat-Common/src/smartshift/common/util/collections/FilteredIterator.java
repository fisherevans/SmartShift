package smartshift.common.util.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author drew
 *
 * @param <T>
 */
public class FilteredIterator<T> implements Iterator<T> {

    /**
     * create a filter on an iterator that will only return accpetable values
     * 
     * @param filter the filter definition
     * @param base the base iterator
     */
    public FilteredIterator(Filter<T> filter, Iterator<T> base) {
        _filter = filter;
        _base = base;
        findNext();
    }
    
    /**
     * set the next value
     */
    private void findNext() {
        if(_hasNext)
            return;
        while(_base.hasNext()) {
            T value = _base.next();
            if(_filter.include(value)) {
                _next = value;
                _hasNext = true;
                break;
            }
        }
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        findNext();
        return _hasNext;
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public T next() {
        findNext();
        if(_hasNext) {
            _hasNext = false;
            return _next;
        }
        throw new NoSuchElementException();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private Filter<T> _filter;
    private Iterator<T> _base;
    private T _next;
    private boolean _hasNext;
}
