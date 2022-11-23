package com.dev.health.entity;

import com.dev.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "health_status")
@NoArgsConstructor
@Getter
public class HealthStatus {
    @Id
    @Column(name = "health_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private int weight;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Activity activity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Target target;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private int needCalorie;
    @Column(nullable = false)
    private int needCarbohydrate;
    @Column(nullable = false)
    private int needProtein;
    @Column(nullable = false)
    private int needFat;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Member member;

    @Builder
    public HealthStatus(int height,int weight,Gender gender,Activity activity, Target target,LocalDate date){
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activity = activity;
        this.target = target;
        this.date = date;
    }

    public void setMember(Member member){
        if(this.member != null){
            this.member.getHealthStatuses().remove(this);
        }
        this.member = member;
        member.getHealthStatuses().add(this);
    }

    public void setNeedCalorie(){
        int BMI = this.height * this.height * this.gender.getValue() * this.activity.getValue();
        this.needCalorie = BMI / 10_000 + this.target.getValue();
    }

    public void setNeedNutrients(){
        this.needCarbohydrate = (int)(this.needCalorie * this.target.getCarbohydratePortion() / 4);
        this.needProtein = (int)(this.needCalorie * this.target.getProteinPortion() / 4);
        this.needFat = (int)(this.needCalorie * this.target.getFatPortion() / 9);
    }

    public void setDate(LocalDate newDate){
        this.date = newDate;
    }


}
