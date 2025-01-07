package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String seatType;
    private int quantity;
    private LocalDateTime purchaseTime;


    Purchase(String userId, String seatType, int quantity){
        this.userId = userId;
        this.seatType = seatType;
        this.quantity = quantity;
    }
}
