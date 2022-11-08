package com.dev.register.entity;

import com.dev.member.entity.Member;
import com.dev.utils.StringArrayConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intake")
@NoArgsConstructor
@Getter
public class Intake {
    @Id
    @Column(name = "intake_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringArrayConverter.class)
    private List<String> breakfast;
    @Convert(converter = StringArrayConverter.class)
    private List<String> lunch;
    @Convert(converter = StringArrayConverter.class)
    private List<String> dinner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Member member;

    @OneToMany(mappedBy = "intake")
    private List<ImageUrl> imageUrls = new ArrayList<>();

}
