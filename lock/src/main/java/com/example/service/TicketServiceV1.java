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
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void purchaseTicket(final Long id, final Long quantity){
        Ticket ticket = ticketRepository.findByIdWithPessimisticLock(id).orElseThrow(
                ()-> new CustomException("비관적락 오류", "오류")
        );
        ticket.decrease(quantity);
        ticketRepository.save(ticket);
        ticketRepository.flush();
    }

    public int getStock(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();

        return ticket.getTotalStock().intValue();
    }

}
