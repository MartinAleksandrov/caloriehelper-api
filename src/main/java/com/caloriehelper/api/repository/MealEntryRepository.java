package com.caloriehelper.api.repository;

import com.caloriehelper.api.entity.MealEntry;
import com.caloriehelper.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MealEntryRepository extends JpaRepository<MealEntry, UUID> {

    List<MealEntry> findByUserAndConsumedAtBetween(User user, Instant startOfDay, Instant endOfDay);

    @Query("SELECT m FROM MealEntry m WHERE m.user = :user AND FUNCTION('DATE', m.consumedAt) = CURRENT_DATE ORDER BY m.consumedAt DESC")
    List<MealEntry> findTodaysMeals(@Param("user") User user);

    List<MealEntry> deleteByUserAndId(User user, UUID mealId);
}