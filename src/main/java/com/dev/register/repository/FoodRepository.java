package com.dev.register.repository;

import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,Long> {
    Optional<Food> findByName(String name);

    Optional<List<Food>> findByCalorieBetween(Double minCalorie, Double maxCalorie);
}
