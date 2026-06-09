package com.caloriehelper.api.dto.meals;

import java.time.LocalDate;

public record DailySummaryResponse(
        LocalDate date,
        Double totalCalories,
        Double totalProtein,
        Double totalCarbs,
        Double totalFat,
        Integer mealCount
) {}