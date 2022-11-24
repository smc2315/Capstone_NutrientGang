package com.dev.health.service;

import com.dev.health.dto.*;
import com.dev.health.entity.HealthStatus;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Dinner;
import com.dev.register.entity.Food;
import com.dev.register.entity.Lunch;
import com.dev.register.repository.BreakfastRepository;
import com.dev.register.repository.DinnerRepository;
import com.dev.register.repository.FoodRepository;
import com.dev.register.repository.LunchRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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

    private final BreakfastRepository breakfastRepository;
    private final LunchRepository lunchRepository;
    private final DinnerRepository dinnerRepository;
    private final FoodRepository foodRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public CalorieInfoResDto getCalorieInfo(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();
        Integer needCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId, date);
        if (needCalorie == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }

        Integer haveCalorie = nutrientStatusRepository.findHaveCalorieByMemberAndDate(userId, date);
        if (haveCalorie == null) {
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
        Integer currentNeedCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId,LocalDate.now());
        NutrientStatus todayNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, LocalDate.now()).get();


        //todo: 정보를 찾을수 없을때 exception 날려야함


        return CalorieReportResDto.builder()
                .maxCalorie(maxCalorie)
                .minCalorie(minCalorie)
                .needCalorie(currentNeedCalorie)
                .todayCalorie(todayNutrientStatus.getCalorie())
                .calorieInfoList(allCalorieInfo)
                .build();
    }

    @Transactional
    public WeightReportResDto getWeightReportInfo(){
        Long userId = SecurityUtil.getCurrentMemberId();
        Integer maxWeight = healthStatusRepository.findMaxWeightByMember(userId);
        Integer minWeight = healthStatusRepository.findMinWeightByMember(userId);
        List<WeightInfoDto> allWeightInfo = healthStatusRepository.findAllWeightInfoByMember(userId);
        HealthStatus todayHealthStatus = healthStatusRepository.findByMemberAndDate(userId, LocalDate.now()).get();

        //todo: 정보를 찾을수 없을때 exception 날려야함


        return WeightReportResDto.builder()
                .maxWeight(maxWeight)
                .minWeight(minWeight)
                .todayWeight(todayHealthStatus.getWeight())
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
    public NutrientWeekPortionInfoResDto getWeekNutrientPortion(LocalDate begin, LocalDate end){
//        todo: 없어도 데이터 넣을수 있게 고쳐야함! 어캐하지?
        Long userId = SecurityUtil.getCurrentMemberId();
        LocalDate date = begin;
        List<NutrientWeekPortionInfoDto> portionList = new ArrayList<>();
        while(!date.equals(end.plusDays(1))){
            Optional<NutrientInfoDto> findHaveNutrientWithDateByMemberAndDate = nutrientStatusRepository.findHaveNutrientWithDateByMemberAndDate(userId, date);
            if(findHaveNutrientWithDateByMemberAndDate.isEmpty()){
                portionList.add(NutrientWeekPortionInfoDto.builder()
                        .date(date)
                        .carbohydratePortion(0.00)
                        .proteinPortion(0.00)
                        .fatPortion(0.00)
                        .build());
                date = date.plusDays(1);
                continue;
            }
            NutrientInfoDto nutrientInfo = findHaveNutrientWithDateByMemberAndDate.get();
            int sumVal = nutrientInfo.getCarbohydrate()+ nutrientInfo.getProtein()+nutrientInfo.getFat();
            if(sumVal == 0){
                portionList.add(NutrientWeekPortionInfoDto.builder()
                        .date(date)
                        .carbohydratePortion(0.00)
                        .proteinPortion(0.00)
                        .fatPortion(0.00)
                        .build());
                date = date.plusDays(1);
                continue;
            }
            portionList.add(NutrientWeekPortionInfoDto.builder()
                    .date(nutrientInfo.getDate())
                    .carbohydratePortion((double)(nutrientInfo.getCarbohydrate())/sumVal)
                    .proteinPortion((double)(nutrientInfo.getProtein())/sumVal)
                    .fatPortion((double)(nutrientInfo.getFat())/sumVal)
                    .build());
            date = date.plusDays(1);
        }

        return NutrientWeekPortionInfoResDto.builder().weekPortionList(portionList).build();
    }

    @Transactional
    public IntakeMealInfoResDto getIntakeMealInfo(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();
        IntakeMealInfoResDto intakeMealInfoResDto = new IntakeMealInfoResDto();

        List<Breakfast> breakfastList = breakfastRepository.findByMemberAndDate(userId, date);
        List<Lunch> lunchList = lunchRepository.findByMemberAndDate(userId, date);
        List<Dinner> dinnerList = dinnerRepository.findByMemberAndDate(userId, date);
        Integer eachNeedCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId, date) / 3;
        intakeMealInfoResDto.setEachNeedCalorie(eachNeedCalorie);

        double breakfastCalorie = 0;
        double lunchCalorie = 0;
        double dinnerCalorie = 0;

        for (Breakfast breakfast : breakfastList) {
            String imgUrl = breakfast.getImgUrl();
            List<String> menus = breakfast.getMenu();
            double calorie = 0;
            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if(findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                calorie += food.getCalorie();
            }
            MealInfoDto mealInfoDto = MealInfoDto.builder()
                    .names(menus)
                    .imgUrl(imgUrl)
                    .calorie((int) calorie)
                    .build();
            intakeMealInfoResDto.setBreakfast(mealInfoDto);
            breakfastCalorie += calorie;
        }

        for (Lunch lunch : lunchList) {
            String imgUrl = lunch.getImgUrl();
            List<String> menus = lunch.getMenu();
            double calorie = 0;
            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if(findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                calorie += food.getCalorie();
            }
            MealInfoDto mealInfoDto = MealInfoDto.builder()
                    .names(menus)
                    .imgUrl(imgUrl)
                    .calorie((int) calorie)
                    .build();
            intakeMealInfoResDto.setLunch(mealInfoDto);
            lunchCalorie += calorie;

        }

        for (Dinner dinner : dinnerList) {
            String imgUrl = dinner.getImgUrl();
            List<String> menus = dinner.getMenu();
            double calorie = 0;
            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if(findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                calorie += food.getCalorie();
            }
            MealInfoDto mealInfoDto = MealInfoDto.builder()
                    .names(menus)
                    .imgUrl(imgUrl)
                    .calorie((int) calorie)
                    .build();
            intakeMealInfoResDto.setDinner(mealInfoDto);
            dinnerCalorie += calorie;
        }

        intakeMealInfoResDto.setBreakfastCalorie((int)breakfastCalorie);
        intakeMealInfoResDto.setLunchCalorie((int)lunchCalorie);
        intakeMealInfoResDto.setDinnerCalorie((int)dinnerCalorie);

        return intakeMealInfoResDto;

    }

    @Transactional
    public MealPortionResDto getMealPortion(LocalDate begin,LocalDate end){
        Long userId = SecurityUtil.getCurrentMemberId();
        LocalDate date = begin;
        int breakfastCount = 0;
        int lunchCount = 0;
        int dinnerCount = 0;
        while(!date.equals(end.plusDays(1))){
            List<Breakfast> breakfastList = breakfastRepository.findByMemberAndDate(userId, date);
            List<Lunch> lunchList = lunchRepository.findByMemberAndDate(userId, date);
            List<Dinner> dinnerList = dinnerRepository.findByMemberAndDate(userId, date);
            if (!breakfastList.isEmpty()){
                breakfastCount++;
            }
            if(!lunchList.isEmpty()){
                lunchCount++;
            }
            if(!dinnerList.isEmpty()){
                dinnerCount++;
            }
            date = date.plusDays(1);
        }
        int sumCount = breakfastCount+lunchCount+dinnerCount;
        if(sumCount == 0){
            return MealPortionResDto.builder()
                    .breakfast(0.00)
                    .lunch(0.00)
                    .dinner(0.00)
                    .build();
        }
        return MealPortionResDto.builder()
                .breakfast((double)breakfastCount/sumCount)
                .lunch((double)lunchCount/sumCount)
                .dinner((double)dinnerCount/sumCount)
                .build();
    }

    @Transactional
    public HadMealInfoResDto getHadMealInfo(LocalDate begin, LocalDate end){
        Long userId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(userId).get();
        LocalDate date= begin;
        List<HadMealDto> breakfastList = new ArrayList<>();
        List<HadMealDto> lunchList = new ArrayList<>();
        List<HadMealDto> dinnerList = new ArrayList<>();
        int id = 1;
        while(!date.equals(end.plusDays(1))){

            boolean breakfastIsEaten = breakfastRepository.existsByMemberAndDate(member, date);
            boolean lunchIsEaten = lunchRepository.existsByMemberAndDate(member, date);
            boolean dinnerIsEaten = dinnerRepository.existsByMemberAndDate(member,date);

            breakfastList.add(HadMealDto.builder().id(id).isEaten(breakfastIsEaten).date(date).build());
            lunchList.add(HadMealDto.builder().id(id).isEaten(lunchIsEaten).date(date).build());
            dinnerList.add(HadMealDto.builder().id(id).isEaten(dinnerIsEaten).date(date).build());

            id++;
            date = date.plusDays(1);
        }
        return HadMealInfoResDto.builder()
                .breakfastList(breakfastList)
                .lunchList(lunchList)
                .dinnerList(dinnerList)
                .build();
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
