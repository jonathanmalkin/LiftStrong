package com.liftstrong.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.liftstrong.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Exercise table.
 */
@Dao
interface ExerciseDao {
    
    /**
     * Insert a new exercise into the database.
     * 
     * @param exercise The exercise to insert.
     * @return The ID of the inserted exercise.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long
    
    /**
     * Insert multiple exercises into the database.
     * 
     * @param exercises The exercises to insert.
     * @return The IDs of the inserted exercises.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>): List<Long>
    
    /**
     * Update an existing exercise in the database.
     * 
     * @param exercise The exercise to update.
     */
    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)
    
    /**
     * Get an exercise by ID.
     * 
     * @param id The ID of the exercise to get.
     * @return The exercise with the given ID, or null if no such exercise exists.
     */
    @Query("SELECT * FROM exercise WHERE id = :id")
    suspend fun getExerciseById(id: Int): ExerciseEntity?
    
    /**
     * Get all exercises in the database.
     * 
     * @return A Flow of all exercises in the database.
     */
    @Query("SELECT * FROM exercise ORDER BY name ASC")
    fun getAllExercises(): Flow<List<ExerciseEntity>>
    
    /**
     * Get all exercises for a specific muscle group.
     * 
     * @param muscleGroup The muscle group to filter by.
     * @return A Flow of all exercises for the given muscle group.
     */
    @Query("SELECT * FROM exercise WHERE muscleGroups LIKE '%' || :muscleGroup || '%' ORDER BY name ASC")
    fun getExercisesByMuscleGroup(muscleGroup: String): Flow<List<ExerciseEntity>>
    
    /**
     * Get all exercises for a specific equipment type.
     * 
     * @param equipment The equipment type to filter by.
     * @return A Flow of all exercises for the given equipment type.
     */
    @Query("SELECT * FROM exercise WHERE equipment LIKE '%' || :equipment || '%' ORDER BY name ASC")
    fun getExercisesByEquipment(equipment: String): Flow<List<ExerciseEntity>>
    
    /**
     * Search for exercises by name.
     * 
     * @param query The search query.
     * @return A Flow of all exercises matching the search query.
     */
    @Query("SELECT * FROM exercise WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchExercises(query: String): Flow<List<ExerciseEntity>>
    
    /**
     * Delete an exercise by ID.
     * 
     * @param id The ID of the exercise to delete.
     */
    @Query("DELETE FROM exercise WHERE id = :id")
    suspend fun deleteExerciseById(id: Int)
}
