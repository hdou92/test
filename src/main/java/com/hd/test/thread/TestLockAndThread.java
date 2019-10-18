package com.hd.test.thread;

import com.hd.test.thread.utils.ThreadPool;

import java.util.concurrent.CompletableFuture;

/**
 * 测试死锁 多线程环境模拟死锁   lockMethod1 、lockMethod2 、startThread 方法组合测试
 *
 * 测试 wait 、 notify 方法  testObjectLock 、 lockMethod
 * wait 线程等待   notify 恢复线程   notifyAll 恢复所有线程
 *
 * 测试线程池 开启有参 无参线程 testThread   testGetParam
 * 线程池会监测线程  所以线程执行完  线程还在运行
 */
public class TestLockAndThread {

    /**
     * 线程池工具对象
     */
    private static final ThreadPool threadPool = new ThreadPool(2);

    /**
     * 恢复线程
     * 执行 Wait 之前，将线程记录在该变量，以便唤醒
     */
    private static Thread threadWait;

    public static void main(String[] args) {
        // 对象交叉死锁
//        startThread();
        // 测试 wait 、 notify
//        testObjectLock();
        // 测试多线池 有参 - 无参 线程
//        testThread();
    }

    /**
     * 测试线程池
     * 1 测试有参线程
     * 2 测试无参线程
     */
    private static void testThread() {
        // 有参线程
        CompletableFuture<String> stringCompletableFuture = threadPool.supplyAsync(TestLockAndThread::testGetParam);
        try {
            System.out.println(stringCompletableFuture.get());
        } catch (Exception e) {
            System.out.println("虽然不大可能 - 但还是异常咯！ 哈哈");
        }

        // 无参线程
        CompletableFuture<Void> voidCompletableFuture = threadPool.runAsync(() -> {
            System.out.println("无参线程开始执行...");
            sleep(1000);
            System.out.println("无参线程执行完成...");
        });
        try {
            Void aVoid = voidCompletableFuture.get();
            System.out.println(aVoid); // 输出 null
        } catch (Exception e) {
            System.out.println("虽然也不大可能 - 但还是异常咯！ 哈哈");
        }
        // 关闭线程池
        threadPool.getExecutor().shutdown();
    }

    /**
     * 该方法是获取线程返回值的方法
     *
     * @return
     */
    public static String testGetParam() {
        return "test 这就是有参线程的`返回值`·····";
    }

    /**
     * 测试 wait 、 notify
     */
    private static void testObjectLock() {
        threadPool.runAsync(() -> {
            try {
                String string = "string";
                Integer integer = 100;
                lockMethod();
                System.out.println(string.length());
                System.out.println(integer.compareTo(100));
            } catch (Exception e) {
            }
        });
        // 休眠1秒  等待线程 线程 阻塞
        sleep(1000);
        System.out.println("接到线程的阻塞 " + threadWait.getState() + " 派出交警~~~~~~");
        sleep(2000);
        synchronized (threadWait) {
            System.out.println("交警叔叔来疏通 `" + threadWait.getName() + "` 交通啦~~~~~~~~~~");
            threadWait.notify();
        }
        System.out.println("交警: 时间到了 ， 下班下班！");
    }


    /**
     * 开启一个对象死锁测试
     */
    private static void startThread() {
        // 开启一个线程 1
        threadPool.runAsync(() -> {
            for (int i = 1; i <= 10; i++) {
                lockMethod1();
            }
            System.out.println("test thread 1 执行完毕！");
        });
        // 开启一个线程 2
        threadPool.runAsync(() -> {
            for (int i = 1; i <= 10; i++) {
                lockMethod2();
            }
            System.out.println("test thread 2 执行完毕！");
        });
    }

    /**
     * 测试方法
     */
    private static void lockMethod() throws Exception {
        synchronized (String.class) {
            System.out.println("哟  这不是 lockMethod 方法的 String 锁里面么   start   开始干活咯~~~~~~");
            synchronized (Integer.class) {
                Thread thread = Thread.currentThread();
                // 线程等待之前要将线程赋值  方便一会恢复
                threadWait = thread;
                synchronized (thread) {
                    System.out.println("线程阻塞 ：   堵车了    快通知交警~~~~~~~~~");
                    thread.wait();
                    System.out.println("线程恢复 ：   继续运行~~~~~~~");
                }
                System.out.println("哟  这不是 lockMethod 方法的 Integer 锁里面么  end   收工下班了~~~~~~");
            }
        }
    }

    /**
     * 测试方法
     * 该方法与 startThread lockMethod2 方法组合使用  模拟死锁
     */
    private static void lockMethod1() {
        synchronized (String.class) {
            System.out.println("method 1 lock String Object ...");
            synchronized (Integer.class) {
                System.out.println("method 1 lock Integer Object ...");
            }
        }
    }

    /**
     * 测试方法
     */
    private static void lockMethod2() {
        synchronized (Integer.class) {
            System.out.println("method 2 lock Integer Object ...");
            synchronized (String.class) {
                System.out.println("method 2 lock String Object ...");
            }
        }
    }

    /**
     * 线程休眠
     *
     * @param time 休眠时间
     */
    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("哎呀~  睡过去了！");
        }
    }
}
