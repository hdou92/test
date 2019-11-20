package com.hd.test.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
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
@SpringBootConfiguration
public class DBConfig {
    @Autowired
    private DBProperties config;

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "c3p0")
    public DataSource dataSource() throws PropertyVetoException {
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
    public SqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageXMLConfigPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "";

        // 设置 mapper 对应的 XML 文件的路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(config.getConfigPath()));
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置 mapper 接口所在的包
        sqlSessionFactoryBean.setTypeAliasesPackage(config.getPackagePath());
        return sqlSessionFactoryBean;
    }

}
