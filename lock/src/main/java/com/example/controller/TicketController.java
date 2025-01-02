package com.example.controller;


import com.example.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket/")
public class TicketController {

    @PostMapping("")
    public ResponseEntity<ResponseDto> purchaseTicket(){
        boolean success = redisService.
    }

}
