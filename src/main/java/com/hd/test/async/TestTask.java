package com.hd.test.async;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;

/**
 * @auther houdu
 * @date 2020/7/8
 */
@Component
@AllArgsConstructor
public class TestTask extends IJobHandler {

    private TestAsync testAsync;

    private Executor taskExecutor;

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void test() throws InterruptedException {
        System.out.println(" TestTask test ： " + Thread.currentThread().getName());
        testAsync.test();
        System.out.println(" TestTask test ： " + Thread.currentThread().getName());
    }

    //    @Scheduled(cron = "0/3 * * * * ?")
    public void test1() throws InterruptedException {
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
        System.out.println("TestTask test1 start： " + Thread.currentThread().getName());
        System.out.println("核心线程数 : " + executor.getCorePoolSize());
        System.out.println("最大线程数 : " + executor.getMaxPoolSize());
        System.out.println("线程池 : " + executor.getThreadPoolExecutor());
        testAsync.test();
        testAsync.test1();
        System.out.println("TestTask test1 end： " + Thread.currentThread().getName());
    }

    @XxlJob("testJobHandler")
    public ReturnT<String> execute(String param) {
        XxlJobLogger.log("hello world.");
        System.out.println(LocalDateTime.now() + Thread.currentThread().getName() + " 执行任务！");
        return ReturnT.SUCCESS;
    }

//    @Scheduled(cron = "0/4 * * * * ?")
//    public void test2() throws InterruptedException {
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(Thread.currentThread().getName()+":" +sdf.format(new Date())+" --> A任务每10秒执行一次");
//        TimeUnit.SECONDS.sleep(40);
//    }
//
//
//    @Scheduled(cron = "0/2 * * * * ?")
//    public void test3() throws InterruptedException {
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(Thread.currentThread().getName()+":" +sdf.format(new Date())+" --> B任务每2秒执行一次");
//    }
}
