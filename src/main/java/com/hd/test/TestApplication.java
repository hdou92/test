package com.hd.test;

import com.hd.test.common.Log4jUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileNotFoundException;
import java.util.Objects;

@EnableScheduling
@MapperScan("com.hd.test.db.dao")
@SpringBootApplication()
public class TestApplication {

    public static void main(String[] args) throws FileNotFoundException {
//        Log4jUtils.setLogConfigFilePath();// 加载日志配置
        System.out.println("uat");
        SpringApplication.run(TestApplication.class, args);
    }

    /**
     * 加载YML格式自定义配置文件
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        //File引入
//        yaml.setResources(new FileSystemResource("/usr/properties/test.yml"));
        //class引入
		yaml.setResources(new ClassPathResource("test.yml"));
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }

}
