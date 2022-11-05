package com.dev.health.entity;

import com.dev.utils.StringArrayConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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

}
