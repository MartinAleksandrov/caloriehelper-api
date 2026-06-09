package com.caloriehelper.api.controller;

import com.caloriehelper.api.dto.meals.DailySummaryResponse;
import com.caloriehelper.api.dto.meals.LogMealRequest;
import com.caloriehelper.api.dto.meals.MealResponse;
import com.caloriehelper.api.entity.MealEntry;
import com.caloriehelper.api.entity.User;
import com.caloriehelper.api.mapper.MealMapper;
import com.caloriehelper.api.security.SecurityHelper;
import com.caloriehelper.api.service.MealService;
import com.caloriehelper.api.utils.NutritionCalculator;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;
    private final SecurityHelper securityHelper;
    private final MealMapper mealMapper;

    public MealController(MealService mealService,
                          SecurityHelper securityHelper,
                          MealMapper mealMapper) {
        this.mealService = mealService;
        this.securityHelper = securityHelper;
        this.mealMapper = mealMapper;
    }

    @PostMapping
    public ResponseEntity<MealResponse> logMeal(
            @Valid @RequestBody LogMealRequest request,
            Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        MealEntry meal = mealService.logMeal(
                user.getId(),
                request.foodId(),
                request.portionGrams(),
                request.consumedAt(),
                request.mealType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mealMapper.toResponse(meal));
    }

    @GetMapping
    public ResponseEntity<List<MealResponse>> getMealsForDate(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        List<MealEntry> meals = mealService.getMealsForDate(user.getId(), targetDate);
        return ResponseEntity.ok(mealMapper.toResponseList(meals));
    }

    @GetMapping("/today")
    public ResponseEntity<List<MealResponse>> getTodaysMeals(Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        List<MealEntry> meals = mealService.getTodaysMeals(user.getId());
        return ResponseEntity.ok(mealMapper.toResponseList(meals));
    }

    @GetMapping("/summary")
    public ResponseEntity<DailySummaryResponse> getDailySummary(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        NutritionCalculator.NutritionTotals totals = mealService.getDailyTotals(user.getId(), targetDate);
        int mealCount = mealService.getMealsForDate(user.getId(), targetDate).size();

        DailySummaryResponse response = new DailySummaryResponse(
                targetDate,
                totals.calories(),
                totals.protein(),
                totals.carbs(),
                totals.fat(),
                mealCount
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(
            @PathVariable UUID id,
            Authentication authentication) {
        User user = securityHelper.getCurrentUser(authentication);
        mealService.deleteMeal(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}