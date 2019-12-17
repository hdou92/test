package com.hd.test.manager;

import com.hd.test.common.ThreadUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Jdk8TestManager {

    private static final Log LOGGER = LogFactory.getLog(Jdk8TestManager.class);

    /**
     * jdk 1.8 ConcurrentHashMap BUG
     */
    private static Map<String, String> map = new ConcurrentHashMap<>();

    /**
     * jdk 1.8 ConcurrentHashMap BUG
     */
    private static Map<String, String> map1 = new ConcurrentHashMap<>();

    /**
     * 测试方法
     */
    public void testJdkBug() {
        ThreadUtils.getThread(true, () -> test()).start();
    }

    /**
     * 测试步骤
     */
    public void test() {
        LOGGER.debug("开始执行测试代码, 测试 JDK1.8 BUG ConcurrentHashMap 导致 cpu 暴涨的问题！");
        map1.put("test1_data", "这是一条测试数据");

        LOGGER.debug("thread name : " + Thread.currentThread().getName());

        /**
         * 执行下面的代码之后  不会有问题
         * 条件： jdk 1.8 , key 要在 map 中找不到
         */
        String test = map.computeIfAbsent("test_data", k -> "test 没有获取到数据,就添加一条数据");
        LOGGER.debug(test);
        /**
         * 执行下面的代码之后  不会有问题
         * 条件： jdk 1.8 , key 要在 map 中找不到
         */
        String test1 = map1.computeIfAbsent("test1_data", k -> "test1 没有获取到数据,就添加一条数据");
        LOGGER.debug(test1);
        /**
         * 执行下面的代码之后  线程会造成死锁 CPU 会爆增
         * 条件： jdk 1.8 , key 要在 map 中找不到
         */
        String s = map.computeIfAbsent("test", key -> {

            map.put("test", "v2");

            return "v1";

        });
        LOGGER.debug("执行完毕！" + s);
    }

}
