package com.dev.register.entity;

import com.dev.recommend.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
@NoArgsConstructor
@Getter
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name",nullable = false,columnDefinition = "MEDIUMTEXT")
    private String name;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private double calorie;
    @Column(nullable = false)
    private double carbohydrate;
    @Column(nullable = false)
    private double protein;
    @Column(nullable = false)
    private double fat;
    @Column(name="food_index")
    private Integer index;
    @Column(name="meal_time")
    private String mealTime;

    @OneToOne(mappedBy = "food")
    private Menu menu;
}
