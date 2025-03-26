package com.example.entity;

import com.example.exception.CustomException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Ticket {

    @Id @GeneratedValue
    private Long id;

    @Setter
    private String seatType;

    //@Version
    //private Long version;

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
