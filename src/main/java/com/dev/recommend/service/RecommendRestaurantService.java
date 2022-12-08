package com.dev.recommend.service;

import com.dev.recommend.dto.MenuDto;
import com.dev.recommend.dto.RestaurantDto;
import com.dev.recommend.dto.RestaurantListDto;
import com.dev.recommend.entity.Restaurant;
import com.dev.recommend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendRestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantListDto findRestaurant(String name){
        List<Restaurant> restaurant = restaurantRepository.findRestaurantByFoodName(name);
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        int i = 0;
        for(Restaurant r : restaurant){
            List<MenuDto> menu = restaurantRepository.findMenuByRestaurant(r.getId());
            RestaurantDto restaurantDto = new RestaurantDto(i,r.getName(),r.getPicture(),r.getLocation(),r.getLatitude(),r.getLongitude(),menu);
            restaurantDtoList.add(restaurantDto);
            i++;
        }
        return RestaurantListDto.builder().
                restaurantDtoList(restaurantDtoList)
                .build();
    }



}
