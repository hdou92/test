package com.hd.test.web;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @auther houdu
 * @date 2020/9/27
 */
@RestController
public class TestPostController {

    @RequestMapping(value = "/test/b2c",method = RequestMethod.POST)
    public String test(@Valid @RequestBody String dto){
        System.out.println(dto);
        return "{\"code\":\"200\",\"success\":\"true\",\"data\":\"true\",\"msg\":\"OK\"";
    }

    @PostMapping(value = "/test/b2c1")
    public String test1(@Valid @RequestBody TmOrderPushDTO dto){
        System.out.println(dto);
        return "{\"code\":\"200\",\"success\":\"true\",\"data\":\"true\",\"msg\":\"OK\"";
    }
}
