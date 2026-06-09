package com.caloriehelper.api.controller;

import com.caloriehelper.api.dto.foods.CreateFoodRequest;
import com.caloriehelper.api.dto.foods.FoodResponse;
import com.caloriehelper.api.entity.Food;
import com.caloriehelper.api.mapper.FoodMapper;
import com.caloriehelper.api.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;
    private final FoodMapper foodMapper;

    public FoodController(FoodService foodService, FoodMapper foodMapper) {
        this.foodService = foodService;
        this.foodMapper = foodMapper;
    }

    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFoods(
            @RequestParam(value = "search", required = false) String search) {
        List<Food> foods = foodService.searchFoods(search);
        return ResponseEntity.ok(foodMapper.toResponseList(foods));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable UUID id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(foodMapper.toResponse(food));
    }

    @PostMapping
    public ResponseEntity<FoodResponse> createFood(@Valid @RequestBody CreateFoodRequest request) {
        Food food = foodService.createFood(
                request.name(),
                request.caloriesPer100g(),
                request.proteinPer100g(),
                request.carbsPer100g(),
                request.fatPer100g()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(foodMapper.toResponse(food));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable UUID id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}