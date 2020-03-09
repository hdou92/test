package com.hd.test.concurrentmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试concurrentMap.computeIfAbsent 死循环问题
 */
public class TestConcurrentMap {

    public static void main(String[] args) {
        // 在 JDK1.8中，这段代码执行会发生 死循环 。注意：使用
        Map<String, Integer> map = new ConcurrentHashMap<>(16);
//        map.computeIfAbsent("A", key ->
//                map.computeIfAbsent("B", key2 -> 42)
//        );

        // compute 如果存在数据就返回value  不存在就添加 然后返回value
        Integer compute = map.compute("A", (k,v) -> 11);
        System.out.println(compute);
        // computeIfAbsent 如果存在数据就返回value  不存在就添加 然后返回value
        Integer integer = map.computeIfAbsent("A", key -> 222);
        System.out.println(integer);
        // computeIfPresent 如果存在数据就返回value  不存在就返回null
        Integer integer1 = map.computeIfPresent("A", (k, v) -> v + 1);
        System.out.println(integer1);

        System.out.println(map);
    }

}
