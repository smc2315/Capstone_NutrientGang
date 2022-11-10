package com.dev.register.repository;

import com.dev.member.entity.Member;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Dinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DinnerRepository extends JpaRepository<Dinner,Long> {
    @Query("select d from Dinner d where d.member.id =:memberId and d.date =:date")
    List<Dinner> findByMemberAndDate(@Param("memberId") Long id, @Param("date")LocalDate date);

    @Query("select d from Dinner d where d.member.id =:memberId and d.date between :begin and :end")
    List<Dinner> findWeekDinnerByMemberAndPeriod(@Param("memberId") Long id, @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    boolean existsByMemberAndDate(Member member, LocalDate date);

}
