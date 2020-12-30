package com.hd.test.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * myBatis plus 加入拦截器
 *
 * @author houdu
 * @date 2020/12/16
 */
@Configuration
public class BatisPlusConfig {

	@Bean
	public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
		//实例化插件
		SqlLoggerInterceptor sqlInterceptor = new SqlLoggerInterceptor();
		//创建属性值
		Properties properties = new Properties();
		properties.setProperty("prop1", "value1");
		//将属性值设置到插件中
		sqlInterceptor.setProperties(properties);
		//将插件添加到SqlSessionFactory工厂
		sqlSessionFactory.getConfiguration().addInterceptor(sqlInterceptor);
		return "interceptor";
	}

}
