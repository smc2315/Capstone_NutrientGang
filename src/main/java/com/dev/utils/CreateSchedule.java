package com.dev.utils;


import com.dev.health.service.HealthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateSchedule {

    private final HealthService healthService;

    @Scheduled(cron = "0 0 0 * * *")
    public void createStatus() {
        log.info("스케쥴러 동작");
        try {
            healthService.createStatuses();
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void testSchedule() {
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        log.info("LocalDate : {}",date);
        log.info("LocalDateTime: {}",dateTime);

    }


}
