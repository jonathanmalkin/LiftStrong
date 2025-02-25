package com.liftstrong.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.liftstrong.data.local.entity.TemplateExerciseEntity
import com.liftstrong.data.local.entity.TemplateSetEntity
import com.liftstrong.data.local.entity.WorkoutTemplateEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the WorkoutTemplate, TemplateExercise, and TemplateSet tables.
 */
@Dao
interface WorkoutTemplateDao {
    
    /**
     * Insert a new workout template into the database.
     * 
     * @param template The workout template to insert.
     * @return The ID of the inserted workout template.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplate(template: WorkoutTemplateEntity): Long
    
    /**
     * Update an existing workout template in the database.
     * 
     * @param template The workout template to update.
     */
    @Update
    suspend fun updateWorkoutTemplate(template: WorkoutTemplateEntity)
    
    /**
     * Insert a new template exercise into the database.
     * 
     * @param templateExercise The template exercise to insert.
     * @return The ID of the inserted template exercise.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplateExercise(templateExercise: TemplateExerciseEntity): Long
    
    /**
     * Insert a new template set into the database.
     * 
     * @param templateSet The template set to insert.
     * @return The ID of the inserted template set.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplateSet(templateSet: TemplateSetEntity): Long
    
    /**
     * Insert multiple template sets into the database.
     * 
     * @param templateSets The template sets to insert.
     * @return The IDs of the inserted template sets.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplateSets(templateSets: List<TemplateSetEntity>): List<Long>
    
    /**
     * Get a workout template by ID.
     * 
     * @param id The ID of the workout template to get.
     * @return The workout template with the given ID, or null if no such workout template exists.
     */
    @Query("SELECT * FROM workout_template WHERE id = :id")
    suspend fun getWorkoutTemplateById(id: Int): WorkoutTemplateEntity?
    
    /**
     * Get all workout templates for a user.
     * 
     * @param userId The ID of the user.
     * @return A Flow of all workout templates for the given user.
     */
    @Query("SELECT * FROM workout_template WHERE userId = :userId ORDER BY name ASC")
    fun getWorkoutTemplatesForUser(userId: Int): Flow<List<WorkoutTemplateEntity>>
    
    /**
     * Get all template exercises for a workout template.
     * 
     * @param templateId The ID of the workout template.
     * @return A list of all template exercises for the given workout template.
     */
    @Query("SELECT * FROM template_exercise WHERE templateId = :templateId ORDER BY `order` ASC")
    suspend fun getTemplateExercisesForTemplate(templateId: Int): List<TemplateExerciseEntity>
    
    /**
     * Get all template sets for a template exercise.
     * 
     * @param templateExerciseId The ID of the template exercise.
     * @return A list of all template sets for the given template exercise.
     */
    @Query("SELECT * FROM template_set WHERE templateExerciseId = :templateExerciseId")
    suspend fun getTemplateSetsForTemplateExercise(templateExerciseId: Int): List<TemplateSetEntity>
    
    /**
     * Delete a workout template by ID.
     * 
     * @param id The ID of the workout template to delete.
     */
    @Query("DELETE FROM workout_template WHERE id = :id")
    suspend fun deleteWorkoutTemplateById(id: Int)
    
    /**
     * Delete a template exercise by ID.
     * 
     * @param id The ID of the template exercise to delete.
     */
    @Query("DELETE FROM template_exercise WHERE id = :id")
    suspend fun deleteTemplateExerciseById(id: Int)
    
    /**
     * Delete a template set by ID.
     * 
     * @param id The ID of the template set to delete.
     */
    @Query("DELETE FROM template_set WHERE id = :id")
    suspend fun deleteTemplateSetById(id: Int)
    
    /**
     * Delete all template exercises for a workout template.
     * 
     * @param templateId The ID of the workout template.
     */
    @Query("DELETE FROM template_exercise WHERE templateId = :templateId")
    suspend fun deleteTemplateExercisesForTemplate(templateId: Int)
    
    /**
     * Delete all template sets for a template exercise.
     * 
     * @param templateExerciseId The ID of the template exercise.
     */
    @Query("DELETE FROM template_set WHERE templateExerciseId = :templateExerciseId")
    suspend fun deleteTemplateSetsForTemplateExercise(templateExerciseId: Int)
    
    /**
     * Get the number of exercises in a workout template.
     * 
     * @param templateId The ID of the workout template.
     * @return The number of exercises in the given workout template.
     */
    @Query("SELECT COUNT(*) FROM template_exercise WHERE templateId = :templateId")
    suspend fun getExerciseCountForTemplate(templateId: Int): Int
}
