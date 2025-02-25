package com.liftstrong.data.repository

import com.liftstrong.data.local.entity.TemplateExerciseEntity
import com.liftstrong.data.local.entity.TemplateSetEntity
import com.liftstrong.data.local.entity.WorkoutTemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for workout template operations.
 */
interface WorkoutTemplateRepository {
    
    /**
     * Get a workout template by ID.
     * 
     * @param id The ID of the workout template to get.
     * @return The workout template with the given ID, or null if no such workout template exists.
     */
    suspend fun getWorkoutTemplateById(id: Int): WorkoutTemplateEntity?
    
    /**
     * Get all workout templates for a user.
     * 
     * @param userId The ID of the user.
     * @return A Flow of all workout templates for the given user.
     */
    fun getWorkoutTemplatesForUser(userId: Int): Flow<List<WorkoutTemplateEntity>>
    
    /**
     * Get all template exercises for a workout template.
     * 
     * @param templateId The ID of the workout template.
     * @return A list of all template exercises for the given workout template.
     */
    suspend fun getTemplateExercisesForTemplate(templateId: Int): List<TemplateExerciseEntity>
    
    /**
     * Get all template sets for a template exercise.
     * 
     * @param templateExerciseId The ID of the template exercise.
     * @return A list of all template sets for the given template exercise.
     */
    suspend fun getTemplateSetsForTemplateExercise(templateExerciseId: Int): List<TemplateSetEntity>
    
    /**
     * Create a new workout template.
     * 
     * @param userId The ID of the user creating the template.
     * @param name The name of the template.
     * @param description Optional description of the template.
     * @return The ID of the created workout template.
     */
    suspend fun createWorkoutTemplate(userId: Int, name: String, description: String? = null): Long
    
    /**
     * Add an exercise to a workout template.
     * 
     * @param templateId The ID of the workout template.
     * @param exerciseId The ID of the exercise.
     * @param order The order of the exercise within the template.
     * @return The ID of the created template exercise.
     */
    suspend fun addExerciseToTemplate(templateId: Int, exerciseId: Int, order: Int): Long
    
    /**
     * Add a set to a template exercise.
     * 
     * @param templateExerciseId The ID of the template exercise.
     * @param weight Optional weight for the set.
     * @param reps The number of repetitions for the set.
     * @param restTime Optional rest time after the set.
     * @return The ID of the created template set.
     */
    suspend fun addSetToTemplateExercise(
        templateExerciseId: Int,
        weight: Float? = null,
        reps: Int,
        restTime: Int? = null
    ): Long
    
    /**
     * Update a workout template.
     * 
     * @param template The workout template to update.
     */
    suspend fun updateWorkoutTemplate(template: WorkoutTemplateEntity)
    
    /**
     * Delete a workout template by ID.
     * 
     * @param id The ID of the workout template to delete.
     */
    suspend fun deleteWorkoutTemplateById(id: Int)
    
    /**
     * Delete a template exercise by ID.
     * 
     * @param id The ID of the template exercise to delete.
     */
    suspend fun deleteTemplateExerciseById(id: Int)
    
    /**
     * Delete a template set by ID.
     * 
     * @param id The ID of the template set to delete.
     */
    suspend fun deleteTemplateSetById(id: Int)
    
    /**
     * Get the number of exercises in a workout template.
     * 
     * @param templateId The ID of the workout template.
     * @return The number of exercises in the given workout template.
     */
    suspend fun getExerciseCountForTemplate(templateId: Int): Int
    
    /**
     * Create a workout from a template.
     * 
     * @param templateId The ID of the template to create a workout from.
     * @param userId The ID of the user creating the workout.
     * @return The ID of the created workout.
     */
    suspend fun createWorkoutFromTemplate(templateId: Int, userId: Int): Long
}
