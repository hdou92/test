package com.hd.test.async;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @auther houdu
 * @date 2020/7/8
 */
@Component
public class TestAsync {

    @Async
    public void test() throws InterruptedException {
        System.out.println("------------ TestAsync test : " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(10);
    }

    @Async
    public void test1() throws InterruptedException {
        System.out.println("------------Exception TestAsync test1 : " + Thread.currentThread().getName());
        int i = 1 / 0;
        TimeUnit.SECONDS.sleep(10);
    }
}
