package com.hd.test.atomic.integer;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic : 原子性
 * 测试 AtomicInteger 并发编程原子类实现  在多线程的情况下 使用 AtomicInteger 数字也不会因多并发而 出现错误 AtomicInteger 中的value 是 volatile 修饰的
 * BlockingQueue 顾名思义 阻塞队列 ，其中 put 方法 如果队列满了之后 线程会阻塞 。 offer 方法 如果队列满了 则返回false 不会阻塞线程; add 方法添加 队列满了 会抛异常
 * poll 取 队列为空时 返回 null  take 取 会阻塞线程
 * 对于Java中的运算操作，例如自增或自减，若没有进行额外的同步操作，在多线程环境下就是线程不安全的。num++解析为num=num+1，明显，这个操作不具备原子性，多线程并发共享这个变量时必然会出现问题。测试代码如下:
 */
public class TestAtomicInteger {

    // 声明一个容量为10的缓存队列 BlockingQueue是阻塞的队列 当队列满了 放入操作将会等待
    private volatile static BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
    // AtomicInteger Integer 的原子类
    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            // incrementAndGet 增加后获取 相当于 ++value  getAndIncrement 获取后增加  相当于 value++
            int k = count.incrementAndGet();
            queue.offer("data : " + k);
            System.out.println(k);
        }
        // count.get() 获取 value
//        System.out.println(count.get());
        System.out.println(queue);
    }

    /**
     * 测试多线程添加队列 AtomicInteger 的值
     */
    private static void testAtomicInteger() {
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 线程添加队列
        execute(executorService);
        // 线程添加队列
        execute(executorService);
        // 线程添加队列
        execute(executorService);
        // 线程池中的线程执行完毕 关闭线程池
        executorService.shutdown();
        while (true) {
            // 线程是否执行完毕
            if (executorService.isTerminated()) {
                //执行到此 队列已经添加满了
//                queue.put("345");     // 会阻塞线程
//                System.out.println(queue.offer("123")); // 不会阻塞线程 没有添加将返回false
                // 队列中所有数据取出 将跳出循环
                while (!queue.isEmpty()) {
                    // 从队列拿出值 先进先出
                    String val = queue.poll();
                    System.out.println("获取数据： " + val + " ...");
                }
                break;
            }
        }
        System.out.println("队列中所有数据都已经读取出来了 ... " + queue);
    }

    /**
     * 执行线程
     *
     * @param executorService 线程池
     */
    private static void execute(ExecutorService executorService) {
        executorService.execute(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    int k = count.incrementAndGet();
                    if (queue.size() == 10) {
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " , count :  " + i + " , " + k);
                    queue.put("data:" + k);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
