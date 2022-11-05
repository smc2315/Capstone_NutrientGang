package com.dev.health.service;

import com.dev.auth.entity.Authority;
import com.dev.health.dto.NutrientDto;
import com.dev.health.entity.*;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthServiceTest {

    @Autowired
    private HealthStatusRepository healthStatusRepository ;
    @Autowired
    private NutrientStatusRepository nutrientStatusRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeAll
    void beforeAll()   {
        LocalDate today = LocalDate.now();
        Member 홍진원 = Member.builder()
                .email("herenever@naver.com")
                .password("12345")
                .username("홍진원")
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(홍진원);

        HealthStatus healthStatus1 = HealthStatus.builder()
                .height(175)
                .weight(70)
                .gender(Gender.MALE)
                .activity(Activity.HARD_ACTIVITY)
                .target(Target.GAIN_MUSCLE)
                .date(today)
                .build();
        healthStatus1.setMember(홍진원);
        healthStatus1.setNeedCalorie();
        healthStatus1.setNeedNutrients();
        healthStatusRepository.save(healthStatus1);

        NutrientStatus nutrientStatus1 = NutrientStatus.builder()
                .calorie(3000)
                .carbohydrate(300)
                .protein(200)
                .fat(30)
                .date(today)
                .build();
        nutrientStatus1.setMember(홍진원);
        nutrientStatusRepository.save(nutrientStatus1);

        HealthStatus healthStatus2 = HealthStatus.builder()
                .height(175)
                .weight(66)
                .gender(Gender.MALE)
                .activity(Activity.HARD_ACTIVITY)
                .target(Target.GAIN_MUSCLE)
                .date(today.plusDays(1))
                .build();
        healthStatus2.setMember(홍진원);
        healthStatus2.setNeedCalorie();
        healthStatus2.setNeedNutrients();
        healthStatusRepository.save(healthStatus2);

        NutrientStatus nutrientStatus2 = NutrientStatus.builder()
                .calorie(3000)
                .carbohydrate(300)
                .protein(200)
                .fat(30)
                .date(today.plusDays(1))
                .build();
        nutrientStatus2.setMember(홍진원);
        nutrientStatusRepository.save(nutrientStatus2);

        HealthStatus healthStatus3 = HealthStatus.builder()
                .height(175)
                .weight(70)
                .gender(Gender.MALE)
                .activity(Activity.SOFT_ACTIVITY)
                .target(Target.GAIN_MUSCLE)
                .date(today.plusDays(2))
                .build();
        healthStatus3.setMember(홍진원);
        healthStatus3.setNeedCalorie();
        healthStatus3.setNeedNutrients();
        healthStatusRepository.save(healthStatus3);

        NutrientStatus nutrientStatus3 = NutrientStatus.builder()
                .calorie(3000)
                .carbohydrate(300)
                .protein(200)
                .fat(30)
                .date(today.plusDays(2))
                .build();
        nutrientStatus3.setMember(홍진원);
        nutrientStatusRepository.save(nutrientStatus3);

        HealthStatus healthStatus4 = HealthStatus.builder()
                .height(175)
                .weight(70)
                .gender(Gender.MALE)
                .activity(Activity.HARD_ACTIVITY)
                .target(Target.GAIN_MUSCLE)
                .date(today.plusDays(3))
                .build();
        healthStatus4.setMember(홍진원);
        healthStatus4.setNeedCalorie();
        healthStatus4.setNeedNutrients();
        healthStatusRepository.save(healthStatus4);

        NutrientStatus nutrientStatus4 = NutrientStatus.builder()
                .calorie(3000)
                .carbohydrate(250)
                .protein(100)
                .fat(30)
                .date(today.plusDays(3))
                .build();
        nutrientStatus4.setMember(홍진원);
        nutrientStatusRepository.save(nutrientStatus4);
    }

    @Test
    @DisplayName("칼로리 계산 테스트")
    void getCalorieInfo()  {
        Long userId = 1L;

        Integer needCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId,LocalDate.now());
        Assertions.assertThat(needCalorie).isEqualTo(3195);
    }

    @Test
    @DisplayName("권장 영양소 계산 테스트 성공")
    void getNutrientInfo()  {
        Long userID = 1L;

        Optional<NutrientDto> needNutrient = healthStatusRepository.findNeedNutrientByMemberAndDate(userID,LocalDate.now());
        NutrientDto test = new NutrientDto(479, 239, 35);
        Assertions.assertThat(needNutrient.get().getCarbohydrate()).isEqualTo(test.getCarbohydrate());
        Assertions.assertThat(needNutrient.get().getProtein()).isEqualTo(test.getProtein());
        Assertions.assertThat(needNutrient.get().getFat()).isEqualTo(test.getFat());

    }

    @Test
    @DisplayName("몸무게 최대값 계산")
    void getMaxWeight() {
        Long userId = 1L;
        Integer maxWeightByMember = healthStatusRepository.findMaxWeightByMember(userId);
        Assertions.assertThat(maxWeightByMember).isEqualTo(70);
    }

    @Test
    @DisplayName("몸무게 최소값")
    void getMinWeight(){
        Long userId = 1L;
        Integer minval = healthStatusRepository.findMinWeightByMember(userId);
        Assertions.assertThat(minval).isEqualTo(66);
    }




}