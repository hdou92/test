package com.hd.test.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.test.db.entity.User;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserMapper extends BaseMapper<User> {
    User queryUser(String id);

    Long getMysqlTimeStamp();

//    void updateByName();
}
