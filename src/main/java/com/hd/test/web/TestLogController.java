package com.hd.test.web;

import com.alibaba.fastjson.JSONObject;
import com.hd.test.db.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther houdu
 * @date 2020/7/24
 */
@Slf4j
@RestController
public class TestLogController {

    @RequestMapping("/testLog")
    public void test() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("error : {}" , e.getMessage() , e );
        }
    }
    @RequestMapping("/testLog1")
    public void test1() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("error : {} , {}" , e.getMessage() , e );
        }
    }

    @RequestMapping("/testAssert")
    public void testAssert() {
        User user = new User();
        user.setId(1l);
        user.setAge(18);
        user.setName("张山");
        user.setEmail("www.qq.com");
        String u = JSONObject.toJSONString(user);
        Object parse = JSONObject.parse(u);
        Assert.isInstanceOf(User.class , parse , "转换错误");
    }
}
