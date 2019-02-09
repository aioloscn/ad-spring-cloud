package com.aiolos.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Aiolos
 * @date 2019-02-05 13:25
 */
public class CommonUtils {

    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {

        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String... args) {

        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
