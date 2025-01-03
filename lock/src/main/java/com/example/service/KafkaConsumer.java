package com.example.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "purchase-requests",groupId = "ticket-group")
    public void processPurchase(String message){
        System.out.println(message);
    }
}
