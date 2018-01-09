package com.dafy.base.gateway.common.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map工具类，代码中尽量不要用new HashMap这样的语句
 *
 * @author lazyman
 * @version V1.0
 */
public class MapUtils {

    public static <K, V> List<V> mapToList(Map<K, V> map) {
        List<V> list = new ArrayList<V>();
        for (K key : map.keySet()) {
            V object = map.get(key);
            list.add(object);
        }
        return list;
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> newHashMap(int size) {
        return new HashMap<K, V>(size);
    }

    public static <K, V> Map<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }

    public static <K, V> Map<K, V> newConcurrentHashMap(int capacity) {
        return new ConcurrentHashMap<K, V>(capacity);
    }

    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> Map<K, V> newLinkedHashMap(int capacity) {
        return new LinkedHashMap<>(capacity);
    }

    public static <K, V> Map<K, V> getHashMap(K[] keys, V[] values) {
        Map<K, V> map = new HashMap<K, V>(8);
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], getValues(values, i));
        }
        return map;
    }

    @SafeVarargs
    public static <T> Map<T, T> getHashMap(T... obj) throws IllegalArgumentException {
        if (obj == null || obj.length % 2 != 0) {
            throw new IllegalArgumentException("param count must be even num");
        }
        Map<T, T> hashMap = newHashMap();
        for (int i = 0; i < obj.length; i += 2) {
            hashMap.put(obj[i], obj[i + 1]);
        }
        return hashMap;
    }

    public static <K, V> Map<K, V> getHashMap(K k, V v) {
        Map<K, V> map = newHashMap();
        map.put(k, v);
        return map;
    }

    public static boolean isEmpty(Map _map) {
        return _map == null || _map.size() == 0;
    }

    public static boolean isNotEmpty(Map _map) {
        return !MapUtils.isEmpty(_map);
    }

    private static <V> V getValues(V[] values, int i) {
        if (values.length > i) {
            return values[i];
        } else {
            return null;
        }
    }
}
