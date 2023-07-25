package com.example.springrabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "STARBUCKS_ORDER")
@Data
@RequiredArgsConstructor
public class StarbucksOrder {

    private @Id @GeneratedValue @JsonIgnore /* https://www.baeldung.com/jackson-ignore-properties-on-serialization */
    Long id;
    @Column(nullable = false)
    private String drink;
    @Column(nullable = false)
    private String milk;
    @Column(nullable = false)
    private String size;
    private double total;
    private String status;
    private String register;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @JsonIgnore
    private StarbucksCard card;

    @OneToMany(mappedBy = "order")
    private List<StarbucksDrink> drinks;
}
