package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity representing an exercise within a workout in the database.
 * 
 * This entity links a workout to an exercise and stores information about the exercise
 * within the context of the workout, such as the order and notes.
 */
@Entity(
    tableName = "workout_exercise",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workoutId"),
        Index("exerciseId")
    ]
)
data class WorkoutExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the workout this exercise belongs to.
     */
    val workoutId: Int,
    
    /**
     * The ID of the exercise.
     */
    val exerciseId: Int,
    
    /**
     * The order of the exercise within the workout.
     */
    val order: Int,
    
    /**
     * Notes about the exercise within the workout.
     */
    val notes: String?
)
