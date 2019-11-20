package com.hd.test.web;

//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hd.test.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MybatisPlusController {


//    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @RequestMapping("/testDB")
    public void test() {
        System.out.println(("----- selectAll method test ------"));
        Map<String, Object> stringObjectMap = userService.myMapper();
        System.out.println(stringObjectMap);
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        qw.select("id");
//        List<User> userList = userMapper.selectList(qw);
//        userList.forEach(System.out::println);
    }
}
