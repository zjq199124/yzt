package com.maizhiyu.yzt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import javax.annotation.Resource;

/*
* @auther zjq
* @date 2023-02-27
* redis反序列化配置类
* */
@Configuration
public class RedisTemplateConfig {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public RedisTemplate<String, String> redisTemplateInit() {
        // 设置序列化 Key 的实例对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置序列化 value 的实例对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}