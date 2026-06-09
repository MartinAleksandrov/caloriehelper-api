package com.caloriehelper.api.mapper;

import com.caloriehelper.api.dto.foods.FoodResponse;
import com.caloriehelper.api.entity.Food;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodResponse toResponse(Food food);

    List<FoodResponse> toResponseList(List<Food> foods);
}