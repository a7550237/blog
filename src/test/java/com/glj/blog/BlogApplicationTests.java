package com.glj.blog;

import com.glj.blog.dao.UserDao;

import com.glj.blog.pojo.User;
import com.glj.blog.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.glj.blog.dao")
public class BlogApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    public void contextLoads() {

    }

}
