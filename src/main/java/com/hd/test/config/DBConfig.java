package com.hd.test.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        ComboPooledDataSource  dataSource = new ComboPooledDataSource();
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
    @Bean
    public MybatisSqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // 设置 mapper 对应的 XML 文件的路径
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(config.getConfigPath()));
        // 设置数据源
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        // 设置 mapper 接口所在的包
//        sqlSessionFactoryBean.setTypeAliasesPackage(config.getPackagePath());
        return mybatisSqlSessionFactoryBean;
    }

}
