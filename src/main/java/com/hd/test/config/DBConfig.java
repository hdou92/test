package com.hd.test.config;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
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
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

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
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(5); //默认值0。 初始化时建立物理连接的个数
        dataSource.setMaxActive(20); // 默认值8。最大连接池数量。
        dataSource.setMinIdle(5);// 最小连接池数量。
        dataSource.setMaxWait(60000); // 获取连接时最大等待时间，单位毫秒。
        dataSource.setMinEvictableIdleTimeMillis(300000); // 连接保持空闲而不被驱逐的最小时间，单位毫秒。
        dataSource.setValidationQuery("SELECT 1"); // 用来检测连接是否有效的sql
        dataSource.setPoolPreparedStatements(true); // 默认值为false。是否缓存preparedStatement，也就是PSCache。
        dataSource.setFilters("stat,wall"); //  属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat、日志用的filter:log4j、防御sql注入的filter:wall。
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20); //默认值-1。要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100。
        dataSource.setUseGlobalDataSourceStat(true); // 使用全局统计
        dataSource.setAsyncInit(true); // 异步初始化
        dataSource.setMaxOpenPreparedStatements(20); // 类似 maxPoolPreparedStatementPerConnectionSize
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean statViewServle(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //IP白名单
        //servletRegistrationBean.addInitParameter("allow","192.168.1.12,127.0.0.1");
        //IP黑名单
        //servletRegistrationBean.addInitParameter("deny","192.168.4.23");
        //控制台用户
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

//    @Bean
//    public StatFilter statFilter() {
//        StatFilter sf = new StatFilter();
//        sf.setSlowSqlMillis(10000);
//        sf.setLogSlowSql(true);
//        return sf;
//    }
//
//    @Bean(name = "wallConfig")
//    public WallConfig wallFilterConfig() {
//        WallConfig wc = new WallConfig();
//        wc.setMultiStatementAllow(true);
//        return wc;
//    }
//
//    @Bean(name = "wallFilter")
//    @DependsOn("wallConfig")
//    public WallFilter wallFilter(WallConfig wallConfig) {
//        WallFilter wfilter = new WallFilter();
//        wfilter.setConfig(wallConfig);
//        wfilter.setDbType("mysql");
//        return wfilter;
//    }

//    @Bean(name = "dataSource")
//    @ConfigurationProperties(prefix = "c3p0")
//    public ComboPooledDataSource dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(config.getDriverClassName());
//        dataSource.setJdbcUrl(config.getUrl());
//        dataSource.setUser(config.getUsername());
//        dataSource.setPassword(config.getPassword());
//        dataSource.setMaxPoolSize(20);
//        dataSource.setMinPoolSize(5);
//        dataSource.setInitialPoolSize(10);
//        dataSource.setMaxIdleTime(300);
//        dataSource.setAcquireIncrement(5);
//        dataSource.setIdleConnectionTestPeriod(60);
//        return dataSource;
//    }

    @Bean
    public MybatisSqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource, PaginationInterceptor paginationInterceptor) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

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
