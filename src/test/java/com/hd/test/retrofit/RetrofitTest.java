package com.hd.test.retrofit;

import com.hd.test.db.entity.User;
import com.hd.test.db.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrofitTest {

    @Autowired
    private UserService userService;

    @Test
    public void test(){
        User user = userService.queryUser();
        System.out.println(user);
    }
}

