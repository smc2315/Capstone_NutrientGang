package com.dev.health.service;

import com.dev.health.dto.*;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthService {
    private final HealthStatusRepository healthStatusRepository;
    private final NutrientStatusRepository nutrientStatusRepository;

    @Transactional
    public CalorieInfoResDto getCalorieInfo(LocalDate date){

        Long userId = SecurityUtil.getCurrentMemberId();
        Integer needCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId,date);
        if(needCalorie == null){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }

        Integer haveCalorie = nutrientStatusRepository.findHaveCalorieByMemberAndDate(userId,date);
        if(haveCalorie == null){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }

        return CalorieInfoResDto.builder()
                .needCalorie(needCalorie)
                .haveCalorie(haveCalorie)
                .build();
    }

    @Transactional
    public NutrientStatusInfoResDto getNutrientInfo(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();

        // 섭취해야할 영양소 계산
        Optional<NutrientDto> needNutrientInfo = healthStatusRepository.findNeedNutrientByMemberAndDate(userId, date);
        if(needNutrientInfo.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        NutrientDto needNutrient = needNutrientInfo.get();


        // 섭취한 영양소 값
        Optional<NutrientDto> haveNutrientInfo = nutrientStatusRepository.findHaveNutrientInfoByMemberAndDate(userId, date);
        if(haveNutrientInfo.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }
        NutrientDto haveNutrient = haveNutrientInfo.get();

        return NutrientStatusInfoResDto.builder()
                .needNutrient(needNutrient)
                .haveNutrient(haveNutrient)
                .build();

    }

    @Transactional
    public CalorieReportResDto getCalorieReportInfo(){
        Long userId = SecurityUtil.getCurrentMemberId();

        Integer maxCalorie = nutrientStatusRepository.findMaxCalorieByMember(userId);
        Integer minCalorie = nutrientStatusRepository.findMinCalorieByMember(userId);
        List<CalorieInfoDto> allCalorieInfo = nutrientStatusRepository.findAllCalorieInfoByMember(userId);
        Integer currentNeedCalorie = healthStatusRepository.findCurrentNeedCalorieByMember(userId,LocalDate.now());

        //todo: 정보를 찾을수 없을때 exception 날려야함


        return CalorieReportResDto.builder()
                .maxCalorie(maxCalorie)
                .minCalorie(minCalorie)
                .needCalorie(currentNeedCalorie)
                .calorieInfoList(allCalorieInfo)
                .build();
    }

    @Transactional
    public WeightReportResDto getWeightReportInfo(){
        Long userId = SecurityUtil.getCurrentMemberId();
        Integer maxWeight = healthStatusRepository.findMaxWeightByMember(userId);
        Integer minWeight = healthStatusRepository.findMinWeightByMember(userId);
        List<WeightInfoDto> allWeightInfo = healthStatusRepository.findAllWeightInfoByMember(userId);

        //todo: 정보를 찾을수 없을때 exception 날려야함


        return WeightReportResDto.builder()
                .maxWeight(maxWeight)
                .minWeight(minWeight)
                .weightInfoList(allWeightInfo)
                .build();

    }

    @Transactional
    public NutrientWeekPortionDto getNutrientPortionInfo(LocalDate begin, LocalDate end){
        Long userId = SecurityUtil.getCurrentMemberId();
        List<NutrientInfoDto> weekHaveNutrientInfo = nutrientStatusRepository.findHaveNutrientInfoByMemberAndPeriod(userId,begin,end);
        return calculateNutrientWeekPortion(weekHaveNutrientInfo);

    }

    @Transactional
    public NutrientWeekInfoResDto getNutrientWeekInfo(LocalDate begin,LocalDate end){
        Long userId = SecurityUtil.getCurrentMemberId();
        List<NutrientInfoDto> weekHaveNutrientInfo = nutrientStatusRepository.findHaveNutrientInfoByMemberAndPeriod(userId,begin,end);
        List<NutrientInfoDto> weekNeedNutrientInfo = healthStatusRepository.findNeedNutrientInfoByMemberAndPeriod(userId, begin, end);
        NutrientDto weekNeedNutrientAvg = calculateWeekNutrient(weekNeedNutrientInfo);
        NutrientDto weekHaveNutrientAvg = calculateWeekNutrient(weekHaveNutrientInfo);

        double carbohydratePortion = (double)weekHaveNutrientAvg.getCarbohydrate()/weekNeedNutrientAvg.getCarbohydrate();
        double proteinPortion = (double)weekHaveNutrientAvg.getProtein()/weekNeedNutrientAvg.getProtein();
        double fatPortion = (double)weekHaveNutrientAvg.getFat()/weekNeedNutrientAvg.getFat();

        carbohydratePortion = Double.parseDouble(String.format("%.2f",carbohydratePortion));
        proteinPortion = Double.parseDouble(String.format("%.2f",proteinPortion));
        fatPortion = Double.parseDouble(String.format("%.2f",fatPortion));

        NutrientWeekPortionDto nutrientPortion = NutrientWeekPortionDto.builder()
                .carbohydratePortion(carbohydratePortion)
                .proteinPortion(proteinPortion)
                .fatPortion(fatPortion)
                .build();
        return NutrientWeekInfoResDto.builder()
                .haveNutrient(weekHaveNutrientAvg)
                .needNutrient(weekNeedNutrientAvg)
                .nutrientPortion(nutrientPortion)
                .build();
    }

    @Transactional
    public List<NutrientWeekPortionInfoDto> getWeekNutrientPortion(LocalDate begin, LocalDate end){
        Long userId = SecurityUtil.getCurrentMemberId();
        List<NutrientInfoDto> weekNutrientInfo= nutrientStatusRepository.findHaveNutrientInfoByMemberAndPeriod(userId, begin, end);
        List<NutrientWeekPortionInfoDto> portionList = new ArrayList<>();
        for (NutrientInfoDto nutrientInfo : weekNutrientInfo) {
            int sumVal = nutrientInfo.getCarbohydrate() + nutrientInfo.getProtein() + nutrientInfo.getFat();
            portionList.add(NutrientWeekPortionInfoDto.builder()
                    .date(nutrientInfo.getDate())
                    .carbohydratePortion((double)(nutrientInfo.getCarbohydrate())/sumVal)
                    .proteinPortion((double)(nutrientInfo.getProtein())/sumVal)
                    .fatPortion((double)(nutrientInfo.getFat())/sumVal)
                    .build()
            );
            log.info("들어간 값: {}",portionList.get(portionList.size()-1).getCarbohydratePortion());
            log.info("들어간 값: {}",portionList.get(portionList.size()-1).getProteinPortion());
            log.info("들어간 값: {}",portionList.get(portionList.size()-1).getFatPortion());

        }
        return portionList;
    }

    private NutrientDto calculateWeekNutrient(List<NutrientInfoDto> weekNutrientInfo){
        int carbohydrateAvg = (int)(weekNutrientInfo.stream()
                .map(NutrientInfoDto::getCarbohydrate)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .filter(v -> v != 0)
                .average()
                .getAsDouble());
        int proteinAvg = (int)(weekNutrientInfo.stream()
                .map(NutrientInfoDto::getProtein)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .filter(v -> v!=0)
                .average()
                .getAsDouble());
        int fatSumAvg = (int)(weekNutrientInfo.stream()
                .map(NutrientInfoDto::getFat)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .filter(v -> v!=0)
                .average()
                .getAsDouble());
        return new NutrientDto(carbohydrateAvg,proteinAvg,fatSumAvg);

    }

    private NutrientWeekPortionDto calculateNutrientWeekPortion(List<NutrientInfoDto> weekNutrientInfo){
        int carbohydrateSum = 0;
        int proteinSum = 0;
        int fatSum = 0;
        for (NutrientInfoDto nutrientInfoDto : weekNutrientInfo) {
            carbohydrateSum +=nutrientInfoDto.getCarbohydrate();
            proteinSum += nutrientInfoDto.getProtein();
            fatSum += nutrientInfoDto.getFat();
        }
        int wholeNutrient = carbohydrateSum+proteinSum+fatSum;
        if (wholeNutrient == 0){
            return NutrientWeekPortionDto.builder()
                    .carbohydratePortion(0.00)
                    .proteinPortion(0.00)
                    .fatPortion(0.00)
                    .build();
        }
        double carbohydratePortion = (double)carbohydrateSum/wholeNutrient;
        double proteinPortion = (double)proteinSum/wholeNutrient;
        double fatPortion = (double)fatSum/wholeNutrient;
        carbohydratePortion = Double.parseDouble(String.format("%.2f",carbohydratePortion));
        proteinPortion = Double.parseDouble(String.format("%.2f",proteinPortion));
        fatPortion = Double.parseDouble(String.format("%.2f",fatPortion));
        return NutrientWeekPortionDto.builder()
                .carbohydratePortion(carbohydratePortion)
                .proteinPortion(proteinPortion)
                .fatPortion(fatPortion)
                .build();
    }

}
