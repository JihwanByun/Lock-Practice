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


}

