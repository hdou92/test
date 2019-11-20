package com.hd.test.db.service;

import com.hd.test.db.dao.UserDao;
import com.hd.test.db.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class UserService {
//    @Autowired
//    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public Map<String,Object> myMapper(){
//        return userDao.queryUser("2");
        return userMapper.queryUser("1");
    }
}
