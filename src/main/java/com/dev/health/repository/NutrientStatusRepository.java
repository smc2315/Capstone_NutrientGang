package com.dev.health.repository;

import com.dev.health.dto.CalorieInfoDto;
import com.dev.health.dto.NutrientDto;
import com.dev.health.entity.NutrientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NutrientStatusRepository extends JpaRepository<NutrientStatus,Long> {

    @Query("select n.calorie from NutrientStatus n where n.member.id = :memberId and n.date = :date")
    Integer findCalorieByMemberAndDateJpql(@Param("memberId") Long id, @Param("date") Date date);

    @Query("select n.carbohydrate, n.protein , n.fat from NutrientStatus n where n.member.id = :memberId and n.date = :date")
    Optional<NutrientDto> findNutrientInfoByMemberAndDateJpql(@Param("memberId") Long id, @Param("date") Date date);

    @Query("select n.date, n.calorie from NutrientStatus n where n.member.id = :memberId")
    List<CalorieInfoDto> findAllCalorieInfoByMember(@Param("memberId") Long id);

    @Query("select max(n.calorie) from NutrientStatus n where n.member.id =:memberId")
     Integer findMaxCalorieByMember(@Param("memberId") Long id);

    @Query("select min(n.calorie) from NutrientStatus n where n.member.id =:memberId")
    Integer findMinCalorieByMember(@Param("memberId") Long id);


}
