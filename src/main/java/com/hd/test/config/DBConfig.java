package com.hd.test.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * mysql 连接配置
 */
@Configuration
public class DBConfig {
    @Autowired
    private DBProperties config;

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "c3p0")
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(config.getDriverClassName());
        dataSource.setJdbcUrl(config.getUrl());
        dataSource.setUser(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setMaxPoolSize(20);
        dataSource.setMinPoolSize(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMaxIdleTime(300);
        dataSource.setAcquireIncrement(5);
        dataSource.setIdleConnectionTestPeriod(60);
        return dataSource;
    }

}
