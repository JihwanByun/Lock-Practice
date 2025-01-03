package com.example.controller;


import com.example.dto.ResponseDto;
import com.example.service.KafkaProducer;
import com.example.service.RedisService;
import com.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket/")
public class TicketController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private TicketService ticketService;


    @PostMapping("/purchase")
    public ResponseEntity<ResponseDto> purchaseTicket(
        @RequestParam String userId,
        @RequestParam String seatType,
        @RequestParam int quantity) {


        ticketService.purchaseTicket(userId,seatType,quantity);

        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "예매 성공"));


    }

}
