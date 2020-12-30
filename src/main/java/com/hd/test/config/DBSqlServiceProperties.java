package com.hd.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * sqlService 连接配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.db.sql-service", ignoreUnknownFields = false)
@PropertySource(value="test.yml")
public class DBSqlServiceProperties {
    private String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url;
    private String username;
    private String password;
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = true;
    private int timeBetweenEvictionRunsMillis = 300000;
    private int minEvictableIdleTimeMillis = 1800000;
    private String validationQuery = "SELECT 1";
}
