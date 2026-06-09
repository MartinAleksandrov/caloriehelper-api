package com.caloriehelper.api.service;

import com.caloriehelper.api.entity.Food;
import com.caloriehelper.api.entity.MealEntry;
import com.caloriehelper.api.entity.MealType;
import com.caloriehelper.api.entity.User;
import com.caloriehelper.api.repository.MealEntryRepository;
import com.caloriehelper.api.utils.NutritionCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class MealService {

    private final MealEntryRepository mealRepository;
    private final UserService userService;
    private final FoodService foodService;

    public MealService(MealEntryRepository mealRepository, UserService userService, FoodService foodService) {
        this.mealRepository = mealRepository;
        this.userService = userService;
        this.foodService = foodService;
    }

    @Transactional
    public MealEntry logMeal(UUID userId, UUID foodId, double portionGrams, Instant consumedAt, MealType mealType) {
        User user = userService.findById(userId);
        Food food = foodService.getFoodById(foodId);

        if (portionGrams <= 0) {
            throw new IllegalArgumentException("Portion must be greater than 0 grams");
        }

        MealEntry meal = new MealEntry(user, food, portionGrams,
                consumedAt != null ? consumedAt : Instant.now(),
                mealType);
        return mealRepository.save(meal);
    }

    @Transactional(readOnly = true)
    public List<MealEntry> getMealsForDate(UUID userId, LocalDate date) {
        User user = userService.findById(userId);
        ZoneId zone = ZoneId.of("UTC");
        Instant startOfDay = date.atStartOfDay(zone).toInstant();
        Instant endOfDay = date.plusDays(1).atStartOfDay(zone).toInstant();
        return mealRepository.findByUserAndConsumedAtBetween(user, startOfDay, endOfDay);
    }

    @Transactional(readOnly = true)
    public List<MealEntry> getTodaysMeals(UUID userId) {
        User user = userService.findById(userId);
        return mealRepository.findTodaysMeals(user);
    }

    @Transactional(readOnly = true)
    public NutritionCalculator.NutritionTotals getDailyTotals(UUID userId, LocalDate date) {
        List<MealEntry> meals = getMealsForDate(userId, date);
        return NutritionCalculator.sumMealEntries(meals);
    }

    @Transactional
    public void deleteMeal(UUID userId, UUID mealId) {
        MealEntry meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found: " + mealId));

        if (!meal.getUser().getId().equals(userId)) {
            throw new SecurityException("Cannot delete another user's meal");
        }

        mealRepository.delete(meal);
    }
}