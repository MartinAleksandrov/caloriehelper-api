package com.caloriehelper.api.dto.foods;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFoodRequest(
        @NotBlank @Size(max = 200) String name,
        @NotNull @DecimalMin("0.0") Double caloriesPer100g,
        @NotNull @DecimalMin("0.0") Double proteinPer100g,
        @NotNull @DecimalMin("0.0") Double carbsPer100g,
        @NotNull @DecimalMin("0.0") Double fatPer100g
) {}