package com.hd.test;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestApplicationTests {

    public static void main(String[] args) {
        new SpringApplication(TestApplicationTests.class).run(args);
    }
    @Test
    public void contextLoads() {
        System.out.println("test" );
    }

}
