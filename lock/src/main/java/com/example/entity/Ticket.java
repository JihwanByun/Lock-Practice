package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Map;

@Entity
@Getter
public class Ticket {

    @Id @GeneratedValue
    private Long id;

    private String seatType;
    private int totalStock;
    private int sold;

}
