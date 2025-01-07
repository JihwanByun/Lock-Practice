package com.example.entity;

import com.example.exception.CustomException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
public class Ticket {

    @Id
    private Long id;

    private String seatType;

//    @Version
//    private Long version;

    private Long totalStock;
    private int sold;

    public Ticket(Long id, Long totalStock) {
        this.id = id;
        this.totalStock = totalStock;
        this.sold = 0;
    }

    public void decrease(final Long count){

        if(this.totalStock - count < 0){
            throw new CustomException("Ticket_SOLDOUT", "재고가 부족합니다");
        }
        this.totalStock -= count;
    }
}
