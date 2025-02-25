package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing an exercise in the database.
 * 
 * This entity stores information about exercises, including name, target muscles, equipment, etc.
 */
@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The name of the exercise.
     */
    val name: String,
    
    /**
     * The muscle groups targeted by the exercise, stored as a comma-separated string.
     */
    val muscleGroups: String,
    
    /**
     * The equipment required for the exercise.
     */
    val equipment: String,
    
    /**
     * Instructions for performing the exercise.
     */
    val instructions: String,
    
    /**
     * The difficulty level of the exercise (e.g., "Beginner", "Intermediate", "Advanced").
     */
    val difficulty: String,
    
    /**
     * Whether the exercise is a custom exercise created by the user.
     */
    val isCustom: Boolean
)
