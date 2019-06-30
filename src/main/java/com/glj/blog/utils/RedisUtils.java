package com.glj.blog.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author guolongjie
 * @since 2019/5/7
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean hasKey(String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        return hasKey;
    }

    public void set(String key, Object value) {
        String json = JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(key, json,1,TimeUnit.DAYS);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T getBean(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        T bean = JSON.parseObject(json, clazz);
        return bean;
    }

    public <T> List<T> getBeanList(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        List<T> beans = JSON.parseArray(json, clazz);
        return beans;
    }


}
