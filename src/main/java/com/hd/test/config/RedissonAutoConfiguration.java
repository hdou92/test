package com.hd.test.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author houdu
 * @date 2021/1/4
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Value("${spring.redisson.host}")
    private String host;

    @Value("${spring.redisson.port}")
    private String port;

    @Value("${spring.redisson.password}")
    private String password;

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        if(password != null && !"".equals(password)){

            config.useSingleServer().setPassword(password);
        }
        //添加主从配置
        return Redisson.create(config);
    }

}
