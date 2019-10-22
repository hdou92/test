package com.hd.test;

import com.hd.test.common.Log4jUtils;
import com.hd.test.common.StringUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws FileNotFoundException {
        Log4jUtils.setLogConfigFilePath();
        SpringApplication.run(TestApplication.class, args);
    }

}
