package com.example.yatzysimulator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiceValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dice1")
    private int dice1;

    @Column(name = "dice2")
    private int dice2;

    @Column(name = "dice3")
    private int dice3;

    @Column(name = "dice4")
    private int dice4;

    @Column(name = "dice5")
    private int dice5;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "category", nullable = true)
    private Integer category;


}
