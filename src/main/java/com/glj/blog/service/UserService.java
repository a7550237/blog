package com.glj.blog.service;

import com.glj.blog.pojo.User;

public interface UserService {
    boolean checkAccount(User user);

    User checkByUsername(String username);

}
