package com.liftstrong.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Data Access Object for the Workout, WorkoutExercise, and WorkoutSet tables.
 */
@Dao
interface WorkoutDao {
    
    /**
     * Insert a new workout into the database.
     * 
     * @param workout The workout to insert.
     * @return The ID of the inserted workout.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity): Long
    
    /**
     * Update an existing workout in the database.
     * 
     * @param workout The workout to update.
     */
    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)
    
    /**
     * Insert a new workout exercise into the database.
     * 
     * @param workoutExercise The workout exercise to insert.
     * @return The ID of the inserted workout exercise.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExerciseEntity): Long
    
    /**
     * Insert a new workout set into the database.
     * 
     * @param workoutSet The workout set to insert.
     * @return The ID of the inserted workout set.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSet(workoutSet: WorkoutSetEntity): Long
    
    /**
     * Insert multiple workout sets into the database.
     * 
     * @param workoutSets The workout sets to insert.
     * @return The IDs of the inserted workout sets.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSets(workoutSets: List<WorkoutSetEntity>): List<Long>
    
    /**
     * Get a workout by ID.
     * 
     * @param id The ID of the workout to get.
     * @return The workout with the given ID, or null if no such workout exists.
     */
    @Query("SELECT * FROM workout WHERE id = :id")
    suspend fun getWorkoutById(id: Int): WorkoutEntity?
    
    /**
     * Get all workouts for a user.
     * 
     * @param userId The ID of the user.
     * @return A Flow of all workouts for the given user.
     */
    @Query("SELECT * FROM workout WHERE userId = :userId ORDER BY date DESC")
    fun getWorkoutsForUser(userId: Int): Flow<List<WorkoutEntity>>
    
    /**
     * Get all workout exercises for a workout.
     * 
     * @param workoutId The ID of the workout.
     * @return A list of all workout exercises for the given workout.
     */
    @Query("SELECT * FROM workout_exercise WHERE workoutId = :workoutId ORDER BY `order` ASC")
    suspend fun getWorkoutExercisesForWorkout(workoutId: Int): List<WorkoutExerciseEntity>
    
    /**
     * Get all workout sets for a workout exercise.
     * 
     * @param workoutExerciseId The ID of the workout exercise.
     * @return A list of all workout sets for the given workout exercise.
     */
    @Query("SELECT * FROM workout_set WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun getWorkoutSetsForWorkoutExercise(workoutExerciseId: Int): List<WorkoutSetEntity>
    
    /**
     * Get all workouts between two dates.
     * 
     * @param startDate The start date.
     * @param endDate The end date.
     * @return A Flow of all workouts between the given dates.
     */
    @Query("SELECT * FROM workout WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkoutsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<WorkoutEntity>>
    
    /**
     * Delete a workout by ID.
     * 
     * @param id The ID of the workout to delete.
     */
    @Query("DELETE FROM workout WHERE id = :id")
    suspend fun deleteWorkoutById(id: Int)
    
    /**
     * Delete a workout exercise by ID.
     * 
     * @param id The ID of the workout exercise to delete.
     */
    @Query("DELETE FROM workout_exercise WHERE id = :id")
    suspend fun deleteWorkoutExerciseById(id: Int)
    
    /**
     * Delete a workout set by ID.
     * 
     * @param id The ID of the workout set to delete.
     */
    @Query("DELETE FROM workout_set WHERE id = :id")
    suspend fun deleteWorkoutSetById(id: Int)
    
    /**
     * Delete all workout exercises for a workout.
     * 
     * @param workoutId The ID of the workout.
     */
    @Query("DELETE FROM workout_exercise WHERE workoutId = :workoutId")
    suspend fun deleteWorkoutExercisesForWorkout(workoutId: Int)
    
    /**
     * Delete all workout sets for a workout exercise.
     * 
     * @param workoutExerciseId The ID of the workout exercise.
     */
    @Query("DELETE FROM workout_set WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun deleteWorkoutSetsForWorkoutExercise(workoutExerciseId: Int)
    
    /**
     * Get the latest workout for an exercise.
     * 
     * @param exerciseId The ID of the exercise.
     * @return The latest workout for the given exercise, or null if no such workout exists.
     */
    @Query("""
        SELECT w.* FROM workout w
        INNER JOIN workout_exercise we ON w.id = we.workoutId
        WHERE we.exerciseId = :exerciseId
        ORDER BY w.date DESC
        LIMIT 1
    """)
    suspend fun getLatestWorkoutForExercise(exerciseId: Int): WorkoutEntity?
    
    /**
     * Get the latest workout sets for an exercise.
     * 
     * @param exerciseId The ID of the exercise.
     * @return A list of the latest workout sets for the given exercise.
     */
    @Transaction
    @Query("""
        SELECT ws.* FROM workout_set ws
        INNER JOIN workout_exercise we ON ws.workoutExerciseId = we.id
        INNER JOIN workout w ON we.workoutId = w.id
        WHERE we.exerciseId = :exerciseId
        ORDER BY w.date DESC, we.`order` ASC
        LIMIT 10
    """)
    suspend fun getLatestWorkoutSetsForExercise(exerciseId: Int): List<WorkoutSetEntity>
}
