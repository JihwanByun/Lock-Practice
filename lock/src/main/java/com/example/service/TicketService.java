package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class TicketService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String TICKET_KEY = "ticket_stock";

    public TicketService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // 초기 재고 설정 (테스트용)
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(TICKET_KEY))) {
            redisTemplate.opsForValue().set(TICKET_KEY, "10"); // 재고 10개 설정
        }
    }

    public boolean purchaseTicket() {
        // Redis 명령을 통해 원자적 감소 처리
        Long stock = redisTemplate.opsForValue().decrement(TICKET_KEY);
        if (stock != null && stock >= 0) {
            return true; // 구매 성공
        } else {
            // 재고 부족 시 롤백
            redisTemplate.opsForValue().increment(TICKET_KEY);
            return false; // 구매 실패
        }
    }

    public int getStock() {
        String stock = redisTemplate.opsForValue().get(TICKET_KEY);
        return stock != null ? Integer.parseInt(stock) : 0;
    }



}
