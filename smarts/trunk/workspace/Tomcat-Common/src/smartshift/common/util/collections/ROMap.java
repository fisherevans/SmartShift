package smartshift.common.util.collections;

import java.util.Map;
import smartshift.common.util.log4j.SmartLogger;

public class ROMap<K, V> {
    private static final SmartLogger logger = new SmartLogger(ROMap.class);
    
    private Map<K, V> _map;
    
    public ROMap(Map<K, V> map) {
        _map = map;
    }
    
    public V get(K key) {
        return _map.get(key);
    }
    
    public ROSet<K> keySet() {
        return new ROSet(_map.keySet());
    }
    
    public ROCollection<V> values() {
        return ROCollection.wrap(_map.values());
    }
}
