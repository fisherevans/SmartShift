package smartshift.common.util.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author drew
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ROFilteredMap<K, V> extends ROMap<K, V>{
    private Filter<K> _filter;
    
    /**
     * @param filter the filter to wrap
     * @param map the map to wrap
     */
    public ROFilteredMap(Filter<K> filter, Map<K, V> map) {
        super(map);
    }
    
    /** (non-Javadoc)
     * @see smartshift.common.util.collections.ROMap#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(K key) {
        if(_filter.include(key))
            return super.containsKey(key);
        return false;
    }
    
    /** (non-Javadoc)
     * @see smartshift.common.util.collections.ROMap#get(java.lang.Object)
     */
    @Override
    public V get(K key) {
        if(_filter.include(key))
            return super.get(key);
        return null;
    }
    
    /** (non-Javadoc)
     * @see smartshift.common.util.collections.ROMap#keySet()
     */
    @Override
    public ROCollection<K> keySet() {
        return new ROFilteredCollection<K>(_filter, super.keySet());
    }
    
    /** (non-Javadoc)
     * @see smartshift.common.util.collections.ROMap#values()
     */
    @Override
    public ROCollection<V> values() {
        List<V> values = new ArrayList<V>();
        for(Entry<K, V> entry : super.entrySet()) {
            if(_filter.include(entry.getKey()))
                values.add(entry.getValue());
        }
        return ROCollection.wrap(values);
    }

}
