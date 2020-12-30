package com.hd.test.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hd.test.db.dao.SqlserverDao;
import com.hd.test.db.dao.UserDao;
import com.hd.test.db.dao.UserMapper;
import com.hd.test.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlserverDao sqlserverDao;

    public User queryUser() {
        List<Map<String, Object>> maps = sqlserverDao.selectSqlServer();
        System.out.println(maps);
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
        return userMapper.selectPage(page, null);
    }

    public boolean install(User user) {
        return userMapper.insert(user) > 0;
    }

    public Long getMysqlTimeStamp() {
        return userMapper.getMysqlTimeStamp();
    }
//    public void updateByName(){
//        userMapper.updateByName();
//    }

    @Transactional(rollbackFor = Exception.class)
    public int update(User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", user.getId());
        return userMapper.update(user, userUpdateWrapper);
    }
}
