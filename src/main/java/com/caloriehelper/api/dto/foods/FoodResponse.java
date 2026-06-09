package com.caloriehelper.api.dto.foods;

import java.time.Instant;
import java.util.UUID;

public record FoodResponse(
        UUID id,
        String name,
        Double caloriesPer100g,
        Double proteinPer100g,
        Double carbsPer100g,
        Double fatPer100g,
        Instant createdAt
) {}