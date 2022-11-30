package com.dev.recommend.entity;

import com.dev.health.entity.HealthStatus;
import com.dev.register.entity.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@Getter
public class Restaurant {
    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_name",nullable = false,columnDefinition = "MEDIUMTEXT")
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private String picture;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Restaurant(String name, String location, double latitude, double longitude, String picture){
        this.name=name;
        this.location=location;
        this.latitude=latitude;
        this.longitude=longitude;
        this.picture=picture;
    }
}
