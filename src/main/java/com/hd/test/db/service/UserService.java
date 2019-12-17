package com.hd.test.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hd.test.db.dao.UserDao;
import com.hd.test.db.dao.UserMapper;
import com.hd.test.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public User queryUser() {
        return userDao.queryUser("1");
    }

    public User queryUser1() {
        return userMapper.queryUser("1");
    }

    public boolean deleteById(String id) {
        return userMapper.deleteById(id) > 0;
    }

    public List<User> select(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>(user);
        return userMapper.selectList(qw);
    }

    public User selectById(String id) {
        return userMapper.selectById(id);
    }

    public List<User> selectAll() {
        return userMapper.selectList(null);
    }

    public IPage<User> selectPage(Page<User> page) {
        return userMapper.selectPage(page,null);
    }

    public boolean install(User user) {
        return userMapper.insert(user) > 0;
    }

    public Long getMysqlTimeStamp(){
        return userMapper.getMysqlTimeStamp();
    }
}
