package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity representing a set within a workout exercise in the database.
 * 
 * This entity stores information about a set, including weight, reps, RPE, and rest time.
 */
@Entity(
    tableName = "workout_set",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workoutExerciseId")
    ]
)
data class WorkoutSetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the workout exercise this set belongs to.
     */
    val workoutExerciseId: Int,
    
    /**
     * The weight used for the set.
     */
    val weight: Float,
    
    /**
     * The number of repetitions performed.
     */
    val reps: Int,
    
    /**
     * The Rate of Perceived Exertion (RPE) for the set, on a scale of 1-10.
     */
    val rpe: Int?,
    
    /**
     * The rest time after the set, in seconds.
     */
    val restTime: Int?,
    
    /**
     * Notes about the set.
     */
    val notes: String?
)
