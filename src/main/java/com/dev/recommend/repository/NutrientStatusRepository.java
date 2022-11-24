package com.dev.recommend.repository;

//import com.dev.health.dto.CalorieInfoDto;
import com.dev.recommend.dto.NutrientDto;
//import com.dev.health.dto.NutrientInfoDto;
import com.dev.health.entity.NutrientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NutrientStatusRepository extends JpaRepository<NutrientStatus,Long> {

    @Query("select n from NutrientStatus n where n.member.id = :memberId and n.date = :date")
    Optional<NutrientStatus> findByMemberAndDate(@Param("memberId") Long id, @Param("date")LocalDate date);

    @Query("select n.calorie from NutrientStatus n where n.member.id = :memberId and n.date = :date")
    Integer findHaveCalorieByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);

    @Query("select new com.dev.health.dto.NutrientDto(n.carbohydrate,n.protein,n.fat) from NutrientStatus n where n.member.id = :memberId and n.date = :date")
    Optional<NutrientDto> findHaveNutrientInfoByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);

//    @Query("select new com.dev.health.dto.CalorieInfoDto(n.date, n.calorie) from NutrientStatus n where n.member.id = :memberId order by n.date desc ")
//    List<CalorieInfoDto> findAllCalorieInfoByMember(@Param("memberId") Long id);

    @Query("select max(n.calorie) from NutrientStatus n where n.member.id =:memberId group by n.member.id")
    Integer findMaxCalorieByMember(@Param("memberId") Long id);

    @Query("select min(n.calorie) from NutrientStatus n where n.member.id =:memberId group by n.member.id")
    Integer findMinCalorieByMember(@Param("memberId") Long id);
//    @Query("select new com.dev.health.dto.NutrientInfoDto(n.date,n.carbohydrate,n.protein,n.fat) from NutrientStatus n where n.member.id =:memberId and n.date= :date")
//    Optional<NutrientInfoDto> findHaveNutrientWithDateByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);
//
//    @Query("select new com.dev.health.dto.NutrientInfoDto(n.date,n.carbohydrate,n.protein,n.fat) from NutrientStatus n where n.member.id =:memberId and n.date between :begin and :end")
//    List<NutrientInfoDto> findHaveNutrientInfoByMemberAndPeriod(@Param("memberId") Long id,@Param("begin") LocalDate begin,@Param("end") LocalDate end);


}
