package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private RedisTemplate redisTemplate;

    public void purchaseTicket(String userId, String seatType, int quantity){

        boolean success = redisService.reduceStock(seatType,quantity);
        if(!success){
            throw new RuntimeException("해당 " + seatType + "의 재고가 없습니다.");
        }

        String message = String.format("User %s purchased %d %s tickets", userId, quantity, seatType);
        kafkaProducer.sendPurchaseEvent("purchase-requests",message);
    }


    public boolean canPurchaseTickets(String userId, int sTickets, int rTickets){
        String userKey = "user:" + userId + ":tickets";
        Map<String, String> ticketData = redisTemplate.opsForHash().entries(userKey);

    }
}
