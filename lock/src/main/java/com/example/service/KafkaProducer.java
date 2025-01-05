package com.example.service;

import com.example.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMApper;

    public void sendPurchaseEvent(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
        } catch (CustomException e) {
            throw new CustomException("NOT SEND"," CAN'T SEND TO KAFKA");
        }
    }
}
