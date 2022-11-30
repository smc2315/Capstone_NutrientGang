package com.dev.recommend.entity;

import com.dev.member.entity.Member;
import com.dev.register.entity.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@NoArgsConstructor
@Getter
public class Menu {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "food_id",nullable = false)
    private Food food;

}
