package smartshift.common.util.collections;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author fevans
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ROMap<K, V> {
    private Map<K, V> _map;
    
    /** creates the ro map
     * @param map the map to wrap
     */
    public ROMap(Map<K, V> map) {
        _map = map;
    }
    
    /**
     * @param key
     * @return calls the wrapped maps: containsKey method
     */
    public boolean containsKey(K key) {
        return _map.containsKey(key);
    }

    /**
     * @param value
     * @return calls the wrapped maps: containsValue method
     */
    public boolean containsValue(V value) {
        return _map.containsValue(value);
    }

    /**
     * @param key
     * @return calls the wrapped maps: get method
     */
    public V get(K key) {
        return _map.get(key);
    }

    /**
     * @return calls the wrapped maps: keySet method
     */
    public ROCollection<K> keySet() {
        return new ROSet<K>(_map.keySet());
    }

    /**
     * @return calls the wrapped maps: values method
     */
    public ROCollection<V> values() {
        return ROCollection.wrap(_map.values());
    }

    /**
     * @return calls the wrapped maps: entrySet method
     */
    public ROSet<Entry<K, V>> entrySet() {
        return new ROSet<Entry<K, V>>(_map.entrySet());
    }
    
}
