package com.caloriehelper.api.mapper;

import com.caloriehelper.api.dto.meals.MealResponse;
import com.caloriehelper.api.entity.MealEntry;
import com.caloriehelper.api.utils.NutritionCalculator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealMapper {

    @Mapping(source = "food.id", target = "foodId")
    @Mapping(source = "food.name", target = "foodName")
    @Mapping(source = ".", target = "calories", qualifiedByName = "calcCalories")
    @Mapping(source = ".", target = "protein", qualifiedByName = "calcProtein")
    @Mapping(source = ".", target = "carbs", qualifiedByName = "calcCarbs")
    @Mapping(source = ".", target = "fat", qualifiedByName = "calcFat")
    MealResponse toResponse(MealEntry meal);

    List<MealResponse> toResponseList(List<MealEntry> meals);

    @Named("calcCalories")
    default double calcCalories(MealEntry meal) {
        return NutritionCalculator.calculateCalories(meal.getFood(), meal.getPortionGrams());
    }

    @Named("calcProtein")
    default double calcProtein(MealEntry meal) {
        return NutritionCalculator.calculateProtein(meal.getFood(), meal.getPortionGrams());
    }

    @Named("calcCarbs")
    default double calcCarbs(MealEntry meal) {
        return NutritionCalculator.calculateCarbs(meal.getFood(), meal.getPortionGrams());
    }

    @Named("calcFat")
    default double calcFat(MealEntry meal) {
        return NutritionCalculator.calculateFat(meal.getFood(), meal.getPortionGrams());
    }
}