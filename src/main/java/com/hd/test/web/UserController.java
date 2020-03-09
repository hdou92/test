package com.hd.test.web;

import com.hd.test.cache.UserCache;
import com.hd.test.db.entity.User;
import com.hd.test.db.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "用户服务", value = "用户服务")
@RequestMapping(value = "user" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    /**
     * 用户缓存实例
     */
    @Autowired
    private UserCache userCache;

    /**
     * 用户实例
     */
    @Autowired
    private UserService userService;

    /**
     * 根据ID获取用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户")
    public User getById(@PathVariable("id") String id){
        return userCache.selectById(id);
    }

    /**
     * 修改用户信息
     * @param user  用户
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID获取用户")
    public void update(@RequestBody User user){
        userService.update(user);
    }
}
