package com.hd.test.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 为了解决ConcurrentHashMap.computeIfAbsent可能会造成死循环而导致CPU100%的问题，一个临时方案
 */
public class ExConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

    private boolean mustLockComputeIfAbsent = false;
    private final Object lockComputeIfAbsentObj = new Object();

    public ExConcurrentHashMap() {
    }

    public ExConcurrentHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ExConcurrentHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public ExConcurrentHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ExConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * computeIfAbsent的时候必须加锁处理
     */
    public void setMustLockComputeIfAbsent(boolean mustLockComputeIfAbsent) {
        this.mustLockComputeIfAbsent = mustLockComputeIfAbsent;
    }

    /**
     * 为了解决ConcurrentHashMap.computeIfAbsent可能会造成死循环而导致CPU100%的问题，一个临时方案
     */
    public V computeIfAbsentEx(K key, Function<? super K, ? extends V> mappingFunction) {
        return computeIfAbsent(key, mappingFunction);
    }

    /**
     * 为了解决ConcurrentHashMap.computeIfAbsent可能会造成死循环而导致CPU100%的问题，一个临时方案
     */
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        if (mustLockComputeIfAbsent) {
            return doLockComputeIfAbsent(key, mappingFunction);
        } else {
            return doUnlockComputeIfAbsent(key, mappingFunction);
        }
    }

    private V doUnlockComputeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V getV = get(key);//ConcurrentHashMap的的value值不能为null的
        if (getV == null) {
            V newValue = mappingFunction.apply(key);
            V value = putIfAbsent(key, newValue);
            if (value == null) {
                return get(key);
            }
            return value;
        }
        return getV;
    }

    private V doLockComputeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V getV = get(key);//ConcurrentHashMap的的value值不能为null的
        if (getV == null) {
            synchronized (lockComputeIfAbsentObj) {
                return doUnlockComputeIfAbsent(key, mappingFunction);
            }
        }
        return getV;
    }

}
