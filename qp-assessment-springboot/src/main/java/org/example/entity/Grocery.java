package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grocery_table")
public class Grocery {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    private Integer price;

    @Column(nullable = false)
    @Getter
    @Setter
    private Integer quantity;

    @Column(nullable = false)
    @Getter
    @Setter
    private Integer inventoryLevel;


    @Column(nullable = true)
    @Getter
    @Setter
    private Long userBookedId;
}
