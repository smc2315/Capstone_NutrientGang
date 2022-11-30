package com.dev.recommend.repository;

import com.dev.member.entity.Member;
import com.dev.recommend.dto.MenuDto;
import com.dev.recommend.entity.Menu;
import com.dev.recommend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
//    @Query("select new com.dev.recommend.dto.MenuDto(h.needCarbohydrate,h.needProtein,h.needFat) from Restaurant r where r.restaurant.id =:memberId and h.date = :date")
//    List<MenuDto> findMenuByFoodName()

}
