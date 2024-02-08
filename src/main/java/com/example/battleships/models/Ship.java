package com.example.battleships.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "ships")
@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private long health;
    private long power;
    private LocalDate created;
    @ManyToOne
    private User owner;

    public Ship(){}

}
