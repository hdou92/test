package com.hd.test.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hd.test.common.JsonUtils;
import com.hd.test.db.entity.User;
import com.hd.test.db.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.LexicalContextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "* MyBatisPlus测试控制器", value = "MyBatisPlus测试服务")
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MybatisPlusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/testDB", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取所有数据 - mybatis plus API")
    public User queryUser() {
        LOGGER.debug("----- select all form mybatis method test ------");
        User user = userService.queryUser();
        LOGGER.debug(JsonUtils.toJsonEx(user));
        return user;
    }

    @RequestMapping(value = "/testDB1", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取所有数据 - mybatis plus API")
    public User queryUser1() {
        LOGGER.debug("----- select all form mybatis plus method test ------");
        User user = userService.queryUser1();
//        LOGGER.debug(JsonUtils.toJsonEx(user));
        LOGGER.debug(user.toString());
        return user;
    }

    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "* 根据ID删除用户 - mybatis plus")
    public boolean deleteById(@PathVariable() String id) {
        LOGGER.debug("----- delete by id method test ------");
        boolean b = userService.deleteById(id);
        LOGGER.debug(JsonUtils.toJsonEx(b));
        return b;
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ApiOperation(value = "* 根据条件获取用户集合 - mybatis plus")
    public List<User> select(@RequestBody User user) {
        LOGGER.debug("----- select by user method test ------");
        List<User> users = userService.select(user);
        LOGGER.debug(JsonUtils.toJsonEx(users));
        return users;
    }

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取所有用户 - mybatis plus")
    public List<User> selectAll() {
        LOGGER.debug("----- select all form mybatis plus method test ------");
        List<User> users = userService.selectAll();
//        LOGGER.debug(JsonUtils.toJsonEx(users));
        return users;
    }

    @RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "* 根据ID获取用户 - mybatis plus")
    public User selectById(@PathVariable String id) {
        LOGGER.debug("----- select by id method test ------");
        User user = userService.selectById(id);
        LOGGER.debug(JsonUtils.toJsonEx(user));
        return user;
    }

    @RequestMapping(value = "/selectPage", method = RequestMethod.POST)
    @ApiOperation(value = "* 分页获取所有用户 - mybatis plus")
    public IPage<User> selectPage(@RequestParam(required = false) Integer pageNo,
                                  @RequestParam(required = false) Integer pageSize) {
        LOGGER.debug("----- select page method test ------");
        IPage<User> iPage = userService.selectPage(new Page<>(pageNo, pageSize));
        LOGGER.debug(JsonUtils.toJsonEx(iPage));
        return iPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "* 新增用户 - mybatis plus")
    public boolean install(@RequestBody User user) {
        LOGGER.debug("----- install user method test ------");
        boolean b = userService.install(user);
        LOGGER.debug(JsonUtils.toJsonEx(b));
        return b;
    }

    @RequestMapping(value = "/getMysqlTime", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取mysql时间 - mybatis plus")
    public Long getMysqlTimeStamp() {
        LOGGER.debug("----- test getMysqlTimeStamp ------");
        Long l = userService.getMysqlTimeStamp();
        LOGGER.debug(JsonUtils.toJsonEx(l));
        return l;
    }

//    @RequestMapping(value = "/updateByName", method = RequestMethod.GET)
//    @ApiOperation(value = "* 根据name修改email,验证主从 - mybatis plus")
//    public void updateByName() {
//        LOGGER.debug("----- test updateByName ------");
//        userService.updateByName();
//    }


}
