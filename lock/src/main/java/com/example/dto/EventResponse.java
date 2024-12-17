package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponse {

    private Long id;

    private final Long ticketLimit;

    EventResponse(Long ticketLimit){
        this.ticketLimit = ticketLimit;
    }

}
