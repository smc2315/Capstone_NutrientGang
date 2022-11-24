package com.dev.recommend.repository;

import com.dev.recommend.dto.NutrientDto;
//import com.dev.health.dto.NutrientInfoDto;
//import com.dev.health.dto.WeightInfoDto;
import com.dev.health.entity.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthStatusRepository extends JpaRepository<HealthStatus,Long> {

    @Query("select h from HealthStatus h where h.member.id = :memberId and h.date =:date")
    Optional<HealthStatus> findByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);
    @Query("select h.needCalorie from HealthStatus h where h.member.id = :memberId and h.date = :date")
    Integer findNeedCalorieByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);
    @Query("select new com.dev.health.dto.NutrientDto(h.needCarbohydrate,h.needProtein,h.needFat) from HealthStatus h where h.member.id =:memberId and h.date = :date")
    Optional<NutrientDto> findNeedNutrientByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);

    @Query("select max(h.weight) from HealthStatus h where h.member.id = :memberId")
    Integer findMaxWeightByMember(@Param("memberId")Long userId);
    @Query("select min(h.weight) from HealthStatus h where h.member.id = :memberId")
    Integer findMinWeightByMember(@Param("memberId") Long userId);
//    @Query("select new com.dev.health.dto.WeightInfoDto(h.date,h.weight) from HealthStatus h where h.member.id = :memberId")
//    List<WeightInfoDto> findAllWeightInfoByMember(@Param("memberId") Long userId);
//    @Query("select new com.dev.health.dto.NutrientInfoDto(h.date,h.needCarbohydrate,h.needProtein,h.needFat) from HealthStatus h where h.member.id=:memberId and h.date between :begin and :end")
//    List<NutrientInfoDto> findNeedNutrientInfoByMemberAndPeriod(@Param("memberId") Long id, @Param("begin") LocalDate begin,@Param("end") LocalDate end);

}
