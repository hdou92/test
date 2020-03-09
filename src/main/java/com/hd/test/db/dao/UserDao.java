package com.hd.test.db.dao;

import com.hd.test.db.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

    User queryUser(String id);

    Long getMysqlTimeStamp();

}
