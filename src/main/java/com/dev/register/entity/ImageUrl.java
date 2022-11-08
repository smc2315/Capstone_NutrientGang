package com.dev.register.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_url")
@NoArgsConstructor
@Getter
public class ImageUrl {
    @Id
    @Column(name = "imageUrl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String url;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MealType type;

    @ManyToOne
    @JoinColumn(name = "intake_id",nullable = false)
    private Intake intake;
}
