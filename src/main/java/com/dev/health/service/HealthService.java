package com.dev.health.service;

import com.dev.health.dto.*;
import com.dev.health.entity.HealthStatus;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.entity.Target;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthService {
    private HealthStatusRepository healthStatusRepository;
    private NutrientStatusRepository nutrientStatusRepository;

    @Transactional
    public CalorieInfoResDto getCalorieInfo(Date date){

        Long userId = SecurityUtil.getCurrentMemberId();
        // 찾아온 건강정보 Optional 값이 null 인지 체크하는 로직을 stream으로 구현하고 싶은데 .get()으로는 내가 만든 예외처리를
        // 할 수가 없다. refactoring 하자
        Optional<HealthStatus> findHealthStatus = healthStatusRepository.findByMemberAndDateJpql(userId, date);
        if(findHealthStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        int needCalorie = calculateCalorie(findHealthStatus.get());


        Integer haveCalorie = nutrientStatusRepository.findCalorieByMemberAndDateJpql(userId,date);
        if(haveCalorie.equals(null)){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }

        return CalorieInfoResDto.builder()
                .needCalorie(needCalorie)
                .haveCalorie(haveCalorie.intValue())
                .build();
    }

    @Transactional
    public NutrientStatusInfoResDto getNutrientInfo(Date date){
        Long userId = SecurityUtil.getCurrentMemberId();

        // 섭취해야할 영양소 계산
        Optional<HealthStatus> findHealthStatus = healthStatusRepository.findByMemberAndDateJpql(userId, date);
        if(findHealthStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        int needCalorie = calculateCalorie(findHealthStatus.get());
        NutrientDto needNutrientInfo = calculateNutrient(findHealthStatus.get().getTarget(),needCalorie);


        // 섭취한 영양소 값
        Optional<NutrientDto> haveNutrientInfo = nutrientStatusRepository.findNutrientInfoByMemberAndDateJpql(userId, date);
        if(haveNutrientInfo.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }
        NutrientDto haveNutrient = haveNutrientInfo.get();

        return NutrientStatusInfoResDto.builder()
                .needNutrient(needNutrientInfo)
                .haveNutrient(haveNutrient)
                .build();

    }

    @Transactional
    public CalorieReportResDto getCalorieReportInfo(Date date){
        Long userId = SecurityUtil.getCurrentMemberId();

        Integer maxCalorie = nutrientStatusRepository.findMaxCalorieByMember(userId);
        Integer minCalorie = nutrientStatusRepository.findMinCalorieByMember(userId);
        List<CalorieInfoDto> allCalorieInfo = nutrientStatusRepository.findAllCalorieInfoByMember(userId);
        Optional<HealthStatus> currentHealthStatus = healthStatusRepository.findCurrentHealthStatusByMemberAndDateJpql(userId);

        if(currentHealthStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        int needCalorie = calculateCalorie(currentHealthStatus.get());

        return CalorieReportResDto.builder()
                .maxCalorie(maxCalorie)
                .minCalorie(minCalorie)
                .needCalorie(needCalorie)
                .calorieInfoList(allCalorieInfo)
                .build();



    }

    private NutrientDto calculateNutrient(Target target, int needCalorie) {
        int needCarbohydrate = (int)(needCalorie * target.getCarbohydratePortion() / 4);
        int needProtein = (int)(needCalorie * target.getProteinPortion() / 4);
        int needFat = (int)(needCalorie * target.getFatPortion() / 4);

        return new NutrientDto(needCarbohydrate,needProtein,needFat);
    }


    private int calculateCalorie(HealthStatus healthStatus){
        int genderValue = healthStatus.getGender().getValue();
        int targetValue = healthStatus.getTarget().getValue();
        int activityValue = healthStatus.getActivity().getValue();
        int height = healthStatus.getHeight();
        int weight = healthStatus.getWeight();
        int BMI = height * height* genderValue * activityValue;

        int needCalorie = BMI / 10000 + targetValue;
        return needCalorie;
    }

}
