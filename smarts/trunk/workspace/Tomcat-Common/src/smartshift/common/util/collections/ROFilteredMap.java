package smartshift.common.util.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ROFilteredMap<K, V> extends ROMap<K, V>{
    private Filter<K> _filter;
    
    public ROFilteredMap(Filter<K> filter, Map<K, V> map) {
        super(map);
    }
    
    public boolean containsKey(K key) {
        if(_filter.include(key))
            return super.containsKey(key);
        return false;
    }
    
    public V get(K key) {
        if(_filter.include(key))
            return super.get(key);
        return null;
    }
    
    public ROCollection<K> keySet() {
        return new ROFilteredCollection<K>(_filter, super.keySet());
    }
    
    public ROCollection<V> values() {
        List<V> values = new ArrayList<V>();
        for(Entry<K, V> entry : super.entrySet()) {
            if(_filter.include(entry.getKey()))
                values.add(entry.getValue());
        }
        return ROCollection.wrap(values);
    }

}
