package com.dev.health.entity;

import com.dev.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "health_status")
@NoArgsConstructor
@Getter
public class HealthStatus {
    @Id
    @Column(name = "healthStatus_id")
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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(unique = true,nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Member member;

    @Builder
    public HealthStatus(int height,int weight,Gender gender,Activity activity, Target target,Date date){
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


}
