package com.hd.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther houdu
 * @date new Date()
 */
@Data
@Configuration
@ConfigurationProperties("spring.task.pool")
public class AsyncProperties {
    //核心线程数
    private int corePoolSize = getAvailableProcessors();
    //最大线程数
    private int maxPoolSize = corePoolSize + 2;
    //线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 60;
    //队列长度
    private int queueCapacity = 10;
    //线程名称前缀
    private String threadNamePrefix = "LBX-AsyncTask-";

    private int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

}
