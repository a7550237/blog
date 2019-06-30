package com.glj.blog.service.impl;

import com.glj.blog.dao.UserDao;
import com.glj.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author guolongjie
 * @since 2019/6/1
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.checkByUsername(username);
        if (user == null){
            throw new RuntimeException("用户名错误!");
        }
        org.springframework.security.core.userdetails.User user1 =
                new org.springframework.security.core.userdetails.User
                        (user.getUserName(),bCryptPasswordEncoder.encode(user.getPassword()), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        return user1;
    }
}
