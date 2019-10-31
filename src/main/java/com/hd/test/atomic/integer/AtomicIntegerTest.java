package com.hd.test.atomic.integer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试AtomicInteger 的原子性
 */
public class AtomicIntegerTest {

    private static final int THREADS_CONUT = 20;
    public static int num = 0;
    public volatile static int count = 0;
    public static AtomicInteger number = new AtomicInteger();

    public static void increase() {
        num++;
        count++;
        number.getAndIncrement();
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("num : " + num);
        System.out.println("count : " + count);
        System.out.println("number : " + number);
    }

}
