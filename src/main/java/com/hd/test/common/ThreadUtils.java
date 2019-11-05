package com.hd.test.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ThreadUtils {

    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }


    /**
     * 获取当前线程的id
     */
    public static long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    /**
     * 获取当前线程的名称
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 获取当前线程的名称
     */
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }

    public static void sleepSecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(StringUtils.exceptionToString(e));
        }
    }


    /**
     * 从Executor中获取处理线程(Cached)
     */
    public static Executor getCachedThreadExecutor() {
        return Executors.newCachedThreadPool();
    }

    /**
     * 从Executor中获取处理线程(Cached)
     */
    public static Executor getSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }


    public static CompletableFuture<Void> runAsync(Runnable run) {
        return CompletableFuture.runAsync(run);
    }

    public static CompletableFuture<Void> runAsync(Runnable run, Executor executor) {
        return CompletableFuture.runAsync(run, executor);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> run) {
        return CompletableFuture.supplyAsync(run);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> run, Executor executor) {
        return CompletableFuture.supplyAsync(run, executor);
    }

    public static <T> T createSingleton(Supplier<T> objGetter, Object lockObj, Supplier<T> objCreator, Consumer<T> objSetter) {
        T obj = objGetter.get();
        if (obj == null) {
            synchronized (lockObj) {
                obj = objGetter.get();
                if (obj == null) {
                    obj = objCreator.get();
                    if (objSetter != null) {
                        objSetter.accept(obj);
                    }
                }
            }
        }
        return obj;
    }



    /**
     * 获取ThreadFactory
     */
    public static ThreadFactory getThreadFactory(boolean daemon) {
        return getThreadFactory(null, daemon);
    }

    /**
     * 获取ThreadFactory
     */
    public static ThreadFactory getThreadFactory(String threadName, boolean daemon) {
        return runnable -> getThread(threadName, daemon, runnable);
    }

    /**
     * 获取ThreadFactory
     */
    public static Thread getThread(boolean daemon, Runnable runnable) {
        return getThread(null, daemon, runnable);
    }

    /**
     * 获取ThreadFactory
     */
    public static Thread getThread(String threadName, boolean daemon, Runnable runnable) {
        // Use our own naming scheme for the threads.
        Thread thread = null;
        if (StringUtils.isEmpty(threadName)) {
//            thread = new Thread(Thread.currentThread().getThreadGroup(), runnable);
            thread = new Thread(runnable);
        } else {
//            thread = new Thread(Thread.currentThread().getThreadGroup(), runnable, threadName);
            thread = new Thread(runnable, threadName);
        }
        // Make workers daemon threads.
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

}
