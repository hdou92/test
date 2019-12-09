package com.hd.test.elastic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * ElasticSearch配置属性
 */
@Configuration
@ConfigurationProperties(prefix = "elastic.config")
@PropertySource(value="test.yml")
public class ElasticProperties {
    /**
     * ES服务地址
     */
    private String[] serverUris;
    /**
     * ES账号
     */
    private String username;
    /**
     * ES密码
     */
    private String password;
    private boolean multiThreaded = true;
    private int defaultMaxTotalConnectionPerRoute = 2;
    private int maxTotalConnection = 20;
    /**
     * 创建索引检测的时间
     */
    private int checkCreateIndexSpan = 60 * 60 * 4;    //默认为4小时
    /**
     * 上一个的数据删除检测的时间间隔（天）
     */
    private int lastMonthDeleteCheckSpan = 20;


    public String[] getServerUris() {
        return serverUris;
    }

    public void setServerUris(String[] serverUris) {
        this.serverUris = serverUris;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMultiThreaded() {
        return multiThreaded;
    }

    public void setMultiThreaded(boolean multiThreaded) {
        this.multiThreaded = multiThreaded;
    }

    public int getDefaultMaxTotalConnectionPerRoute() {
        return defaultMaxTotalConnectionPerRoute;
    }

    public void setDefaultMaxTotalConnectionPerRoute(int defaultMaxTotalConnectionPerRoute) {
        this.defaultMaxTotalConnectionPerRoute = defaultMaxTotalConnectionPerRoute;
    }

    public int getMaxTotalConnection() {
        return maxTotalConnection;
    }

    public void setMaxTotalConnection(int maxTotalConnection) {
        this.maxTotalConnection = maxTotalConnection;
    }

    public int getCheckCreateIndexSpan() {
        return checkCreateIndexSpan;
    }

    public void setCheckCreateIndexSpan(int checkCreateIndexSpan) {
        this.checkCreateIndexSpan = checkCreateIndexSpan;
    }

    public int getLastMonthDeleteCheckSpan() {
        return lastMonthDeleteCheckSpan;
    }

    public void setLastMonthDeleteCheckSpan(int lastMonthDeleteCheckSpan) {
        this.lastMonthDeleteCheckSpan = lastMonthDeleteCheckSpan;
    }

    @Override
    public String toString() {
        return "ElasticProperties{" +
                "serverUris=" + Arrays.toString(serverUris) +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", multiThreaded=" + multiThreaded +
                ", defaultMaxTotalConnectionPerRoute=" + defaultMaxTotalConnectionPerRoute +
                ", maxTotalConnection=" + maxTotalConnection +
                ", checkCreateIndexSpan=" + checkCreateIndexSpan +
                ", lastMonthDeleteCheckSpan=" + lastMonthDeleteCheckSpan +
                '}';
    }
}
