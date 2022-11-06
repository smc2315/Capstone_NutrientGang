package com.dev.health.entity;

import com.dev.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Nutrient_status")
@NoArgsConstructor
@Getter
public class NutrientStatus {
    @Id
    @Column(name = "nutrientStatus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int calorie;
    @Column(nullable = false)
    private int carbohydrate;
    @Column(nullable = false)
    private int protein;
    @Column(nullable = false)
    private int fat;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Member member;

    @Builder
    public NutrientStatus(int calorie,int carbohydrate,int protein,int fat,LocalDate date){
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.date = date;
    }

    public void setMember(Member member){
        if(this.member != null){
            this.member.getNutrientStatuses().remove(this);
        }
        this.member = member;
        member.getNutrientStatuses().add(this);
    }
}
