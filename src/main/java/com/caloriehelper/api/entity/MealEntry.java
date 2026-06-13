package com.caloriehelper.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "meal_entries", indexes = {
        @Index(name = "idx_meal_user_consumed", columnList = "user_id, consumed_at")
})
public class MealEntry {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_meal_user"))
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_id", nullable = false, foreignKey = @ForeignKey(name = "fk_meal_food"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Food food;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    @Column(name = "portion_grams", nullable = false)
    private Double portionGrams;

    @NotNull
    @Column(name = "consumed_at", nullable = false)
    private Instant consumedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false, length = 20)
    private MealType mealType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        if (this.consumedAt == null) {
            this.consumedAt = Instant.now();
        }
    }

    // Constructors
    public MealEntry() {}

    public MealEntry(User user, Food food, Double portionGrams,
                     Instant consumedAt, MealType mealType) {
        this.user = user;
        this.food = food;
        this.portionGrams = portionGrams;
        this.consumedAt = consumedAt;
        this.mealType = mealType;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }

    public Double getPortionGrams() { return portionGrams; }
    public void setPortionGrams(Double portionGrams) { this.portionGrams = portionGrams; }

    public Instant getConsumedAt() { return consumedAt; }
    public void setConsumedAt(Instant consumedAt) { this.consumedAt = consumedAt; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}