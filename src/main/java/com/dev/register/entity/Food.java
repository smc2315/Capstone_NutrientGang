package com.dev.register.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "food")
@NoArgsConstructor
@Getter
public class Food {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double calorie;
    @Column(nullable = false)
    private double carbohydrate;
    @Column(nullable = false)
    private double protein;
    @Column(nullable = false)
    private double fat;
}
