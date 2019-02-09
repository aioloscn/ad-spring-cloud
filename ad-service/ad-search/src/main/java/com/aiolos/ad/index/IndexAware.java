package com.aiolos.ad.index;

/**
 * @author Aiolos
 * @date 2019-02-04 19:27
 */
public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
