package com.hd.test.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.test.db.entity.User;

public interface UserMapper extends BaseMapper<User> {
    User queryUser(String id);

    Long getMysqlTimeStamp();
}
