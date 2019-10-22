package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestRestController {

//    @RequestMapping("testApi")
//    public RestResult<String> testRetrofit(){
//        RestResult<String> restResult = new RestResult<>();
//        restResult.setData("rest return data");
//        return restResult;
//    }

    @RequestMapping("testApi")
    @ResponseBody
    public String testRetrofit(){
        return "rest return data";
    }
}
