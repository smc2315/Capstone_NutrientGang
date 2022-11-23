package com.dev.member.entity;

import com.dev.auth.entity.Authority;
import com.dev.health.entity.HealthStatus;
import com.dev.health.entity.NutrientStatus;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Dinner;
import com.dev.register.entity.Lunch;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(name="pass_word",nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<HealthStatus> healthStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<NutrientStatus> nutrientStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Breakfast> breakfasts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Lunch> lunches = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Dinner> dinners = new ArrayList<>();
    @Builder
    public Member(String email,String password,String username,Authority authority){
        this.email = email;
        this.password = password;
        this.username = username;
        this.authority = authority;
    }


}
