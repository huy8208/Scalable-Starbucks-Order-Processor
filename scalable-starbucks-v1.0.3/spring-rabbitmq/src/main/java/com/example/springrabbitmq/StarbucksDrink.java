package com.example.springrabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "STARBUCKS_DRINK")
@Data
@RequiredArgsConstructor
public class StarbucksDrink {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long drinkID;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private StarbucksOrder order;

    private String status;
}
