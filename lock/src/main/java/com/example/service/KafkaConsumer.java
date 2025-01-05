package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "purchase-requests",groupId = "ticket-group")
    public void processPurchase(ConsumerRecord<String, String> record){

    }
}
