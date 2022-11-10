package com.dev.register.repository;

import com.dev.member.entity.Member;
import com.dev.register.entity.Breakfast;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BreakfastRepository extends JpaRepository<Breakfast,Long> {
    @Query("select b from Breakfast b where b.member.id =:memberId and b.date =:date")
    List<Breakfast> findByMemberAndDate(@Param("memberId") Long id, @Param("date") LocalDate date);

    @Query("select b from Breakfast b where b.member.id =:memberId and b.date between :begin and :end")
    List<Breakfast> findWeekBreakfastByMemberAndPeriod(@Param("memberId") Long id,@Param("begin") LocalDate begin,@Param("end") LocalDate end);

    boolean existsByMemberAndDate(Member member, LocalDate date);
}
