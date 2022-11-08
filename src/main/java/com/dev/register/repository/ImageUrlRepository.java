package com.dev.register.repository;

import com.dev.register.entity.ImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageUrlRepository extends JpaRepository<ImageUrl,Long> {
    @Query("select i from ImageUrl i where i.intake.id =:intakeId")
    Optional<ImageUrl> findByIntake(@Param("intakeId") Long id);
}
