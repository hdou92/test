package com.hd.test.db.dao;

import com.hd.test.db.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User queryUser(String id);

    Long getMysqlTimeStamp();

}
