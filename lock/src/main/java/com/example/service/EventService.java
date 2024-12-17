package com.example.service;


import com.example.dto.EventResponse;
import com.example.dto.TicketResponse;
import com.example.entity.Event;
import com.example.entity.Ticket;
import com.example.repository.EventRepository;
import com.example.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public EventResponse createEvent(Long eventTicket){
        Event event = new Event(eventTicket);
        eventRepository.save(event);
        return new EventResponse(event.getId(), event.getTicketLimit());
    }

    @Transactional
    public TicketResponse createTicket(final Long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow();
        if(event.isClosed()){
            throw new RuntimeException("마감 되었습니다");
        }

        Ticket ticket = ticketRepository.save(new Ticket(event));

        return new TicketResponse(ticket.getId());
    }

}

