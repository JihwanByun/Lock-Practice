package com.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private Long ticketLimit;

    @OneToMany
    private List<Ticket> tickets;

    public boolean isClosed(){
        return tickets.size() > ticketLimit;
    }

    public Event(Long ticketLimit){
        this.ticketLimit = ticketLimit;
    }


}
