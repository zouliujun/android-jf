package cn.change365.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 2015/8/13.
 */
public class CollectionUtil {

    public static <K, V> Map<K, V> reverseMap(Map<V, K> map, boolean canReplace){

        Map<K, V> newMap = new HashMap<>();
        for(Map.Entry<V, K> entry : map.entrySet()){
            if(!canReplace && newMap.containsKey(entry.getValue())){
                throw new RuntimeException("have duplicates value " + entry.getValue());
            }
            newMap.put(entry.getValue(), entry.getKey());
        }
        return newMap;
    }

}
