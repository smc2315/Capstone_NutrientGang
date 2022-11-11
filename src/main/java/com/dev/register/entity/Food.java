package com.dev.register.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fooddb")
@NoArgsConstructor
@Getter
public class Food {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "음식명",nullable = false)
    private String name;
    @Column(name = "1회제공량",nullable = false,columnDefinition = "MEDIUMTEXT")
    private int oncePerServe;
    @Column(name = "열량",nullable = false)
    private double calorie;
    @Column(name = "탄수화물",nullable = false)
    private double carbohydrate;
    @Column(name = "단백질",nullable = false)
    private double protein;
    @Column(name = "지방",nullable = false)
    private double fat;
}
