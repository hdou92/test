package com.hd.test.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ZCollectionUtils {

    /**
     * 判断集合为空
     *
     * @param list 集合
     * @return 结果
     */
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断集合为空
     *
     * @param list 集合
     * @return 结果
     */
    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length <= 0;
    }

    /**
     * 判断集合为空
     *
     * @param map 集合
     * @return 结果
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断集合不为空
     *
     * @param list 集合
     * @return 结果
     */
    public static boolean isNotEmpty(Collection<?> list) {
        return list != null && !list.isEmpty();
    }

    /**
     * 判断集合不为空
     *
     * @param list 集合
     * @return 结果
     */
    public static <T> boolean isNotEmpty(T[] list) {
        return list != null && list.length > 0;
    }

    /**
     * 判断集合不为空
     *
     * @param map 集合
     * @return 结果
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * to list
     */
    public static <T> List<T> toList(T[] values) {
        List<T> list = new ArrayList<>();
        for (T val : values) {
            list.add(val);
        }
        return list;
    }

    /**
     * to list
     */
    public static <T> List<T> toList(T value) {
        List<T> list = new ArrayList<>();
        list.add(value);
        return list;
    }

    /**
     * to list
     */
    public static <T> List<T> toList(T value, boolean isValueCanNull) {
        if (!isValueCanNull && value == null) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();
        list.add(value);
        return list;
    }

    /**
     * 将List转换成Map
     *
     * @param list     list
     * @param getKey   获取key
     * @param getValue 获取value
     * @param map      map
     * @param <TK>     key
     * @param <TV>     value
     * @return 结果
     */
    public static <TK, TV, TVL> Map<TK, TVL> toMap(Collection<TV> list, Function<TV, TK> getKey,
                                                   Function<TV, TVL> getValue, Map<TK, TVL> map) {
        for (TV l : list) {
            map.put(getKey.apply(l), getValue.apply(l));
        }
        return map;
    }
}
