package com.hd.test.db.dao;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.test.db.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper //extends BaseMapper<User>
{
    Map<String,Object> queryUser(String id);
}
