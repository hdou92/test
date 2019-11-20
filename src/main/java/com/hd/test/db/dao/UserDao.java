package com.hd.test.db.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao {

    Map<String,Object> queryUser(String id);

}
