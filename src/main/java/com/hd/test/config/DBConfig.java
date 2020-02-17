package com.hd.test.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.hd.test.interceptor.MycatInterceptor;
import com.hd.test.web.MybatisPlusController;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * mysql 连接配置
 */
@Configuration
public class DBConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConfig.class);

    @Autowired
    private DBProperties config;

    @Autowired
    private MycatInterceptor mycatInterceptor;

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

    @Bean
    public MybatisSqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource, PaginationInterceptor paginationInterceptor) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        System.out.println("设置 mapper 对应的 XML 文件的路径");
        LOGGER.info("设置 mapper 对应的 XML 文件的路径-----1");
        LOGGER.debug("设置 mapper 对应的 XML 文件的路径----2");

        // 设置 mapper 对应的 XML 文件的路径
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(config.getConfigPath()));
        // 设置数据源
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        // 设置 MyBatis-Plus 分页插件
        Interceptor[] plugins = {paginationInterceptor, mycatInterceptor};
        mybatisSqlSessionFactoryBean.setPlugins(plugins);
        // 设置 mapper 接口所在的包
//        sqlSessionFactoryBean.setTypeAliasesPackage(config.getPackagePath());
        return mybatisSqlSessionFactoryBean;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setDialectType("mysql");
    }

}
