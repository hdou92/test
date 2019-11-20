package com.hd.test;

import com.hd.test.db.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootApplication
public class TestApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
        Map<String, Object> map = userService.myMapper();
        System.out.println(map);
    }

}
