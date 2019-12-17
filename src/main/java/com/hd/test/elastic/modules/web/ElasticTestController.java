package com.hd.test.elastic.modules.web;

import com.hd.test.elastic.modules.service.ElasticUserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class ElasticTestController {

    @Autowired
    private ElasticUserModelService elasticUserModelService;

    @RequestMapping("testUserModel")
    public void testUserModel(){
    }
}
