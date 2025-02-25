package com.liftstrong.data.repository

import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Repository for workout operations.
 */
interface WorkoutRepository {
    
    /**
     * Get a workout by ID.
     * 
     * @param id The ID of the workout to get.
     * @return The workout with the given ID, or null if no such workout exists.
     */
    suspend fun getWorkoutById(id: Int): WorkoutEntity?
    
    /**
     * Get all workouts for a user.
     * 
     * @param userId The ID of the user.
     * @return A Flow of all workouts for the given user.
     */
    fun getWorkoutsForUser(userId: Int): Flow<List<WorkoutEntity>>
    
    /**
     * Get all workout exercises for a workout.
     * 
     * @param workoutId The ID of the workout.
     * @return A list of all workout exercises for the given workout.
     */
    suspend fun getWorkoutExercisesForWorkout(workoutId: Int): List<WorkoutExerciseEntity>
    
    /**
     * Get all workout sets for a workout exercise.
     * 
     * @param workoutExerciseId The ID of the workout exercise.
     * @return A list of all workout sets for the given workout exercise.
     */
    suspend fun getWorkoutSetsForWorkoutExercise(workoutExerciseId: Int): List<WorkoutSetEntity>
    
    /**
     * Get all workouts between two dates.
     * 
     * @param startDate The start date.
     * @param endDate The end date.
     * @return A Flow of all workouts between the given dates.
     */
    fun getWorkoutsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<WorkoutEntity>>
    
    /**
     * Create a new workout.
     * 
     * @param userId The ID of the user creating the workout.
     * @param name The name of the workout.
     * @param date The date of the workout.
     * @param notes Optional notes for the workout.
     * @return The ID of the created workout.
     */
    suspend fun createWorkout(userId: Int, name: String, date: LocalDateTime, notes: String? = null): Long
    
    /**
     * Add an exercise to a workout.
     * 
     * @param workoutId The ID of the workout.
     * @param exerciseId The ID of the exercise.
     * @param order The order of the exercise within the workout.
     * @param notes Optional notes for the exercise.
     * @return The ID of the created workout exercise.
     */
    suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int, order: Int, notes: String? = null): Long
    
    /**
     * Add a set to a workout exercise.
     * 
     * @param workoutExerciseId The ID of the workout exercise.
     * @param weight The weight used for the set.
     * @param reps The number of repetitions performed.
     * @param rpe Optional Rate of Perceived Exertion for the set.
     * @param restTime Optional rest time after the set.
     * @param notes Optional notes for the set.
     * @return The ID of the created workout set.
     */
    suspend fun addSetToWorkoutExercise(
        workoutExerciseId: Int,
        weight: Float,
        reps: Int,
        rpe: Int? = null,
        restTime: Int? = null,
        notes: String? = null
    ): Long
    
    /**
     * Update a workout.
     * 
     * @param workout The workout to update.
     */
    suspend fun updateWorkout(workout: WorkoutEntity)
    
    /**
     * Delete a workout by ID.
     * 
     * @param id The ID of the workout to delete.
     */
    suspend fun deleteWorkoutById(id: Int)
    
    /**
     * Delete a workout exercise by ID.
     * 
     * @param id The ID of the workout exercise to delete.
     */
    suspend fun deleteWorkoutExerciseById(id: Int)
    
    /**
     * Delete a workout set by ID.
     * 
     * @param id The ID of the workout set to delete.
     */
    suspend fun deleteWorkoutSetById(id: Int)
    
    /**
     * Get the latest workout for an exercise.
     * 
     * @param exerciseId The ID of the exercise.
     * @return The latest workout for the given exercise, or null if no such workout exists.
     */
    suspend fun getLatestWorkoutForExercise(exerciseId: Int): WorkoutEntity?
    
    /**
     * Get the latest workout sets for an exercise.
     * 
     * @param exerciseId The ID of the exercise.
     * @return A list of the latest workout sets for the given exercise.
     */
    suspend fun getLatestWorkoutSetsForExercise(exerciseId: Int): List<WorkoutSetEntity>
    
    /**
     * Complete a workout by setting its duration.
     * 
     * @param workoutId The ID of the workout to complete.
     * @param duration The duration of the workout in minutes.
     */
    suspend fun completeWorkout(workoutId: Int, duration: Int)
}
