package com.caloriehelper.api.dto.meals;

import com.caloriehelper.api.entity.MealType;

import java.time.Instant;
import java.util.UUID;

public record MealResponse(
        UUID id,
        UUID foodId,
        String foodName,
        Double portionGrams,
        Double calories,
        Double protein,
        Double carbs,
        Double fat,
        Instant consumedAt,
        MealType mealType
) {}