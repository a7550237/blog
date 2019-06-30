package com.glj.blog.service.impl;

import com.glj.blog.dao.UserDao;
import com.glj.blog.pojo.User;
import com.glj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author guolongjie
 * @since 2019/4/30
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean checkAccount(User user) {
        return userDao.login(user);
    }

    @Override
    public User checkByUsername(String username) {
        return userDao.checkByUsername(username);
    }



}
