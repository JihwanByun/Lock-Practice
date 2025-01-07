package com.example.controller;


import com.example.dto.ResponseDto;
import com.example.service.RedisService;
import com.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket() {
        boolean success = ticketService.purchaseTicket();
        if (success) {
            return ResponseEntity.ok("티켓 구매 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("티켓 재고 부족");
        }
    }

    @GetMapping("/stock")
    public ResponseEntity<Integer> getStock() {
        return ResponseEntity.ok(ticketService.getStock());
    }
}
