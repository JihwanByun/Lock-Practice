package com.example.service;

import com.example.entity.Ticket;
import com.example.exception.CustomException;
import com.example.repository.TicketRepository;
import jakarta.persistence.LockModeType;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketServiceV1 {

    private final TicketRepository ticketRepository;

    @Transactional
    public void purchaseTicket(final Long id, final Long quantity){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticket.decrease(quantity);
        ticketRepository.save(ticket);
        ticketRepository.flush();
        int i = 0;
        System.out.println("flush" + i);
    }

    public int getStock(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();

        return ticket.getTotalStock().intValue();
    }

}
