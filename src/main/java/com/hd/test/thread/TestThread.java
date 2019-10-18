package com.hd.test.thread;

/**
 * 测试 volatile 修饰符
 * 修饰了 volatile 之后, flag 变量变成了一个共享变量 ,
 * flag 发生变化之后,所有线程都会接收到变化之后的值
 * <p>
 * ThreadA 简称 A 检测 flag ， flag = true 则跳出
 * ThreadB 简称 B 将 flag 赋值为 true
 * <p>
 * 测试结果： 不加 volatile 执行 A 线程 - 再执行 B 线程 。输出结果：打印 “threadB : flag is true”
 * 不加 volatile 执行 A 线程 - 再执行 B 线程 A 线程 休眠 1 秒。输出结果：打印 “threadB : flag is true” “threadA : flag is true”
 * 加 volatile 执行 A 线程 - 再执行 B 线程 。输出结果：打印 “threadB : flag is true” “threadA : flag is true”
 * <p>
 * 总结：每个线程都有自己的缓存区   A 一直在检测 flag ， flag 的检测速度远远大于 从内存读取速度 所以一直读取的缓存的值 所以一直没有跳出死循环  B 将 flag 的值改为 true  而 A 也没有跳出
 * 而加上 volatile 修饰符之后 说明 flag 是所有线程共享的变量 所以 B 改变了之后  A 也就跳出了循环
 * 而休眠之后缓存的值 也被刷新了
 */
public class TestThread {

    public static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();

        new Thread(threadA, "threadA").start();
        Thread.sleep(1000l);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();
    }

    static class ThreadA extends Thread {
        public void run() {
            while (true) {
//         Thread.sleep(1000); // 休眠之后 貌似在内存中读取完成
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
                    break;
                }
            }
        }
    }

    static class ThreadB extends Thread {
        public void run() {
            flag = true;
            System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
        }
    }

}
