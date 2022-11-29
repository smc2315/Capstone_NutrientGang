package com.dev.register.repository;

import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,Long> {
    Optional<Food> findByName(String name);

    Optional<List<Food>> findByCalorieBetween(Double minCalorie, Double maxCalorie);

    Optional<List<Food>> findByCalorieLessThanAndMealTimeContaining(Double maxCalorie, String mealTime);

    Optional<List<Food>> findByCalorieBetweenAndMealTimeContaining(Double minCalorie,Double maxCalorie, String mealTime);

    Optional<List<Food>> findByCalorieLessThanAndCarbohydrateLessThanAndProteinLessThanAndFatLessThanAndMealTimeContaining(Double calorie,Double carbohydrate,Double protein,Double fat,String mealTime);

    Optional<List<Food>> findByCalorieBetweenAndCarbohydrateBetweenAndProteinBetweenAndFatBetweenAndMealTimeContaining(Double minCalorie,Double maxCalorie,Double minCarbohydrate,Double maxCarbohydrate,Double minProtein,Double maxProtein,Double minFat,Double maxFat,String mealTime);

}