package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public boolean reduceStock(String ticketType, int quantity) {
        String lockKey = "lock:stock:" + ticketType;
        String stockKey = "stock:" + ticketType;

        String lockValue = UUID.randomUUID().toString();

        try{
            Boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 10, TimeUnit.SECONDS);
            if(Boolean.FALSE.equals(lockAcquired)){
                return false;
            }

            Long newStock = redisTemplate.opsForValue().decrement(stockKey, quantity);
            if(newStock < 0 ){
                redisTemplate.opsForValue().increment(stockKey, quantity);
                return false;
            }
            return true;

        } finally {
            String currentLockValue = redisTemplate.opsForValue().get(lockKey).toString();
            if(lockValue.equals(currentLockValue)){
                redisTemplate.delete(lockKey);
            }
        }

    }

    public boolean isRequestAllowed(String userId){
        String rateKey = "rate:user:" + userId;
        Long requests =redisTemplate.opsForValue().increment(rateKey,1);

        if(requests == 1){
            redisTemplate.expire(rateKey, 1, TimeUnit.SECONDS);
        }

        return requests <= 10;
    }
}
