package cn.change365.framework.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.change365.framework.utils.CollectionUtil;

/**
 * Created by Jack on 2015/8/13.
 */
public class ReverseMap<K, V> implements Map<K, V>{

    private Map<V, K> reverseMap;
    private Map<K, V> map;

    public ReverseMap(Class<? extends Map> clazz) throws Exception {
        map = (Map) clazz.newInstance();
        reverseMap = (Map)clazz.newInstance();
    }

    public ReverseMap(){
        map = new HashMap<>();
        reverseMap = new HashMap<>();
    }

    public Map<V, K> getReverseMap(){
        return reverseMap;
    }

    @Override
    public void clear() {
        map.clear();
        reverseMap.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    public K getKey(Object value){
        return reverseMap.get(value);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    public Set<V> valueSet(){
        return reverseMap.keySet();
    }

    @Override
    public V put(K key, V value) {
        if(reverseMap.containsKey(value)){
            throw new RuntimeException("have duplicates value " + value);
        }
        reverseMap.put(value, key);
        return map.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        Map<? extends V, ? extends K> temp = CollectionUtil.reverseMap(map, true);
        this.map.putAll(map);
        this.reverseMap.putAll(temp);
    }

    @Override
    public V remove(Object key) {
        reverseMap.remove(map.get(key));
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }
}
