package com.caloriehelper.api.service;

import com.caloriehelper.api.entity.Food;
import com.caloriehelper.api.repository.FoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Transactional
    public Food createFood(String name, Double calories, Double protein, Double carbs, Double fat) {
        Food food = new Food(name, calories, protein, carbs, fat);
        return foodRepository.save(food);
    }

    @Transactional(readOnly = true)
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Food getFoodById(UUID id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Food> searchFoods(String query) {
        if (query == null || query.isBlank()) {
            return foodRepository.findAll();
        }
        return foodRepository.findByNameContainingIgnoreCase(query);
    }

    @Transactional
    public void deleteFood(UUID id) {
        if (!foodRepository.existsById(id)) {
            throw new IllegalArgumentException("Food not found: " + id);
        }
        foodRepository.deleteById(id);

    }
}