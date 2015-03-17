package smartshift.common.util.collections;

import java.util.Map;
import java.util.Map.Entry;
import smartshift.common.util.log4j.SmartLogger;

public class ROMap<K, V> {
    private static final SmartLogger logger = new SmartLogger(ROMap.class);
    
    private Map<K, V> _map;
    
    public ROMap(Map<K, V> map) {
        _map = map;
    }
    
    public boolean containsKey(K key) {
        return _map.containsKey(key);
    }
    
    public boolean containsValue(V value) {
        return _map.containsValue(value);
    }
    
    public V get(K key) {
        return _map.get(key);
    }
    
    public ROCollection<K> keySet() {
        return new ROSet<K>(_map.keySet());
    }
    
    public ROCollection<V> values() {
        return ROCollection.wrap(_map.values());
    }
    
    public ROSet<Entry<K, V>> entrySet() {
        return new ROSet<Entry<K, V>>(_map.entrySet());
    }
    
}
