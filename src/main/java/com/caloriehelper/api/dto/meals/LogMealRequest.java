package com.caloriehelper.api.dto.meals;

import com.caloriehelper.api.entity.MealType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record LogMealRequest(
        @NotNull UUID foodId,
        @NotNull @DecimalMin(value = "0.1") Double portionGrams,
        Instant consumedAt,
        @NotNull MealType mealType
) {}