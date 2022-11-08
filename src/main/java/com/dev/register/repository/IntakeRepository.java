package com.dev.register.repository;

import com.dev.register.entity.Intake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IntakeRepository extends JpaRepository<Intake, Long> {

    @Query("select i from Intake i where i.member.id =:memberId and i.date =:date")
    Optional<Intake> findByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);
}
