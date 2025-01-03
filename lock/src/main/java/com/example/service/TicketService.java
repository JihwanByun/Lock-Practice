package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void purchaseTicket(String userId, String seatType, int quantity){

        boolean success = redisService.reduceStock(seatType,quantity);
        if(!success){
            throw new RuntimeException("해당 " + seatType + "의 재고가 없습니다.");
        }

        String message = String.format("User %s purchased %d %s tickets", userId, quantity, seatType);
        kafkaProducer.sendPurchaseEvent("purchase-requests",message);
    }
}
