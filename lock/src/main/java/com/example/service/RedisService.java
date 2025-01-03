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

    public boolean reduceStock(String seatType, int quantity){
        String stockKey = "ticket:stock:" + seatType;
        String script =
                "local stock = tonumber(redis.call('GET;, KEYS[1])) " +
                        "if stock >= tonumber(ARGV[1]) then " +
                        "redis.call('DECRBY',KEYS[1], ARGV[1]) return 1 else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(stockKey), quantity);
        return result == 1;
    }
}
