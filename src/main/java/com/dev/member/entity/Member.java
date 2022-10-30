package com.dev.member.entity;

import com.dev.auth.entity.Authority;
import com.dev.health.entity.HealthStatus;
import com.dev.health.entity.NutrientStatus;
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
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<HealthStatus> healthStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<NutrientStatus> nutrientStatuses = new ArrayList<>();

    @Builder
    public Member(String email,String password,String username,Authority authority){
        this.email = email;
        this.password = password;
        this.username = username;
        this.authority = authority;
    }


}
