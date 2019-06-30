package com.glj.blog.dao;

import com.glj.blog.pojo.User;

import java.util.List;

public interface UserDao {

    //登录验证
    boolean login(User user);

    User checkByUsername(String username);

}
