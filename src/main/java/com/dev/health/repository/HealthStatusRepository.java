package com.dev.health.repository;

import com.dev.health.dto.NutrientDto;
import com.dev.health.entity.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface HealthStatusRepository extends JpaRepository<HealthStatus,Long> {

    @Query("select h from HealthStatus h where h.member.id = :memberId and h.date = :date")
    Optional<HealthStatus> findByMemberAndDateJpql(@Param("memberId") Long id, @Param("date") Date date);

    @Query("select h from HealthStatus h where h.member.id = :memberId and h.date =" +
            "(select max(hx.date) from HealthStatus hx where hx.member.id = :memberId)")
    Optional<HealthStatus> findCurrentHealthStatusByMemberAndDateJpql(@Param("memberId") Long id);


}
