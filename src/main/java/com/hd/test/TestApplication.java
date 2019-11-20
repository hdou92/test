package com.hd.test;

import com.hd.test.common.Log4jUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;

//@MapperScan("com.hd.test.db.dao")
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws FileNotFoundException {
        Log4jUtils.setLogConfigFilePath();// 加载日志配置
        SpringApplication.run(TestApplication.class, args);
    }

    /**
     * 加载YML格式自定义配置文件
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new FileSystemResource("/usr/properties/test.yml"));//File引入
		yaml.setResources(new ClassPathResource("test.yml"));//class引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

}
