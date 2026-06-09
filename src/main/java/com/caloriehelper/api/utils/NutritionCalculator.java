package com.caloriehelper.api.utils;

import com.caloriehelper.api.entity.Food;
import com.caloriehelper.api.entity.MealEntry;

import java.util.List;

public class NutritionCalculator {

    private NutritionCalculator() {
        // Utility class - prevent instantiation
    }

    public static double calculateCalories(Food food, double portionGrams) {
        return (portionGrams / 100.0) * food.getCaloriesPer100g();
    }

    public static double calculateProtein(Food food, double portionGrams) {
        return (portionGrams / 100.0) * food.getProteinPer100g();
    }

    public static double calculateCarbs(Food food, double portionGrams) {
        return (portionGrams / 100.0) * food.getCarbsPer100g();
    }

    public static double calculateFat(Food food, double portionGrams) {
        return (portionGrams / 100.0) * food.getFatPer100g();
    }

    public static NutritionTotals sumMealEntries(List<MealEntry> meals) {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;

        for (MealEntry meal : meals) {
            Food food = meal.getFood();
            double grams = meal.getPortionGrams();
            totalCalories += calculateCalories(food, grams);
            totalProtein += calculateProtein(food, grams);
            totalCarbs += calculateCarbs(food, grams);
            totalFat += calculateFat(food, grams);
        }

        return new NutritionTotals(totalCalories, totalProtein, totalCarbs, totalFat);
    }

    public record NutritionTotals(double calories, double protein, double carbs, double fat) {}
}