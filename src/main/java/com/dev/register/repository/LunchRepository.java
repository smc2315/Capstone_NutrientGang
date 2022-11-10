package com.dev.register.repository;

import com.dev.member.entity.Member;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LunchRepository extends JpaRepository<Lunch,Long> {
    @Query("select l from Lunch l where l.member.id =:memberId and l.date =:date")
    List<Lunch> findByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);

    @Query("select l from Lunch l where l.member.id =:memberId and l.date between :begin and :end")
    List<Lunch> findWeekLunchByMemberAndPeriod(@Param("memberId") Long id, @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    boolean existsByMemberAndDate(Member member, LocalDate date);

}
