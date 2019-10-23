package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import com.hd.test.retrofit.data.Info;
import com.hd.test.retrofit.data.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRestController.class);

    @RequestMapping(value = "testApiString", method = RequestMethod.GET)
    public RestResult<String> testApiString() {
        LOGGER.debug("测试返回值为<RestResult<String>> 的无参 GET 接口<testApiString>");
        RestResult<String> restResult = new RestResult<>();
        restResult.setData("rest api return RestResult<String> data ...");
        return restResult;
    }

    @RequestMapping(value = "testString", method = RequestMethod.GET)
    public String testString() {
        LOGGER.debug("测试返回值为<String> 的无参 GET 接口<testString>");
        return "rest api return String data ...";
    }

    @RequestMapping(value = "testApiPostObject", method = RequestMethod.POST)
    public RestResult<Info<Test>> testApiPostObject() {
        LOGGER.debug("测试返回值为<RestResult<Info<Test>>> 的无参 GET 接口<testApiPostObject>");
        List<Test> list = new ArrayList<>();
        list.add(new Test("1", 1));
        list.add(new Test("2", 2));
        list.add(new Test("3", 3));
        list.add(new Test("4", 4));
        RestResult<Info<Test>> restResult = new RestResult<>();
        Info<Test> info = new Info<>();
        info.setName("test");
        info.setList(list);
        restResult.setData(info);
        return restResult;
    }

    @RequestMapping(value = "testParam", method = RequestMethod.POST)
    public RestResult<Info<Test>> testParam(String name) {
        LOGGER.debug("测试返回值为<RestResult<Info<Test>>> 的有参 <" + name + "> POST 接口<testParam>");
        List<Test> list = new ArrayList<>();
        list.add(new Test("有参测试", 1));
        list.add(new Test("有参测试1", 2));
        list.add(new Test("有参测试2", 3));

        RestResult<Info<Test>> restResult = new RestResult<>();
        Info<Test> info = new Info<>();
        info.setName(name);
        info.setList(list);
        restResult.setData(info);
        return restResult;
    }

    @RequestMapping(value = "testParamApi", method = RequestMethod.POST)
    public RestResult<Info<Test>> testParamApi(Test test) {
        LOGGER.debug("测试返回值为<RestResult<Info<Test>>> 的有参 <" + test + "> POST 接口<testParamApi>");
        List<Test> list = new ArrayList<>();
        list.add(new Test("测试", 1));
        list.add(new Test("测试1", 2));
        list.add(new Test("测试2", 3));

        RestResult<Info<Test>> restResult = new RestResult<>();
        Info<Test> info = new Info<>();
        info.setName(test.toString());
        info.setList(list);
        restResult.setData(info);
        return restResult;
    }
}
