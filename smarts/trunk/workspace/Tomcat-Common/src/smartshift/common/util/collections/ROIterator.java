package smartshift.common.util.collections;

import java.util.Iterator;

/**
 * A read only iterator to wrap a normal iterator, overriding
 *  the remove function
 * @author drew
 *
 * @param <T>
 */
public class ROIterator<T> implements Iterator<T>{

    private Iterator<T> _iterator;
    
    /**
     * @param toWrap
     */
    public ROIterator(Iterator<T> toWrap) {
        _iterator = toWrap;
    }
    
    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return _iterator.hasNext();
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public T next() {
        return _iterator.next();
    }

    /**
     * @throws UnsupportedOperationException always
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
