package com.dafy.base.gateway.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/19
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Class<T> tClass, Collection<T> collection) {
        T[] ts = (T[]) Array.newInstance(tClass, collection.size());
        return collection.toArray(ts);

    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("aa");
        set.add("bb");
        set.add("cc");
        String[] array = toArray(String.class, set);
        System.out.println(array.length);
    }
}
