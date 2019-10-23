package com.hd.test;

import com.hd.test.common.Log4jUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws FileNotFoundException {
        Log4jUtils.setLogConfigFilePath();// 加载日志配置
        SpringApplication.run(TestApplication.class, args);
    }

}
