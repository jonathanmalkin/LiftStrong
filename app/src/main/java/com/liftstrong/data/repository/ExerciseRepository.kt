package com.liftstrong.data.repository

import com.liftstrong.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for exercise operations.
 */
interface ExerciseRepository {
    
    /**
     * Get an exercise by ID.
     * 
     * @param id The ID of the exercise to get.
     * @return The exercise with the given ID, or null if no such exercise exists.
     */
    suspend fun getExerciseById(id: Int): ExerciseEntity?
    
    /**
     * Get all exercises.
     * 
     * @return A Flow of all exercises.
     */
    fun getAllExercises(): Flow<List<ExerciseEntity>>
    
    /**
     * Get exercises by muscle group.
     * 
     * @param muscleGroup The muscle group to filter by.
     * @return A Flow of exercises for the given muscle group.
     */
    fun getExercisesByMuscleGroup(muscleGroup: String): Flow<List<ExerciseEntity>>
    
    /**
     * Get exercises by equipment.
     * 
     * @param equipment The equipment to filter by.
     * @return A Flow of exercises for the given equipment.
     */
    fun getExercisesByEquipment(equipment: String): Flow<List<ExerciseEntity>>
    
    /**
     * Search exercises by name.
     * 
     * @param query The search query.
     * @return A Flow of exercises matching the search query.
     */
    fun searchExercises(query: String): Flow<List<ExerciseEntity>>
    
    /**
     * Insert a new exercise.
     * 
     * @param exercise The exercise to insert.
     * @return The ID of the inserted exercise.
     */
    suspend fun insertExercise(exercise: ExerciseEntity): Long
    
    /**
     * Insert multiple exercises.
     * 
     * @param exercises The exercises to insert.
     * @return The IDs of the inserted exercises.
     */
    suspend fun insertExercises(exercises: List<ExerciseEntity>): List<Long>
    
    /**
     * Update an existing exercise.
     * 
     * @param exercise The exercise to update.
     */
    suspend fun updateExercise(exercise: ExerciseEntity)
    
    /**
     * Delete an exercise by ID.
     * 
     * @param id The ID of the exercise to delete.
     */
    suspend fun deleteExerciseById(id: Int)
    
    /**
     * Populate the database with default exercises.
     */
    suspend fun populateDefaultExercises()
}
