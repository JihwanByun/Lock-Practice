package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public boolean reduceStock(String key, int decrement) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(script, Collections.singletonList(key), decrement);
        return result != null && result == 1;
    }
}
