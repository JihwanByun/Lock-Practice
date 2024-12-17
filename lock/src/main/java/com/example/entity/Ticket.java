package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Ticket {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Ticket(Event event){
        this.event = event;
    }
}
