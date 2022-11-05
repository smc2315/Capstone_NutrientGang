package com.dev.utils;

import com.dev.health.entity.HealthStatus;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateSchedule {


    private final HealthStatusRepository healthStatusRepository;
    private final NutrientStatusRepository nutrientStatusRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void createStatus() {
        log.info("스케쥴러 작동");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.plusDays(-1);
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            Optional<HealthStatus> yesterdayInfo = healthStatusRepository.findByMemberAndDate(member.getId(), yesterday);
            if(yesterdayInfo.isEmpty()){
                continue;
            }
            HealthStatus healthStatus = yesterdayInfo.get();
            healthStatus.setDate(today);
            healthStatusRepository.save(healthStatus);
            NutrientStatus nutrientStatus = NutrientStatus.builder()
                    .calorie(0)
                    .carbohydrate(0)
                    .protein(0)
                    .fat(0)
                    .date(today)
                    .build();
            nutrientStatus.setMember(member);
            nutrientStatusRepository.save(nutrientStatus);
        }
//        todo: 섭취기록도 업데이트 하도록 추가한다.
    }


}
