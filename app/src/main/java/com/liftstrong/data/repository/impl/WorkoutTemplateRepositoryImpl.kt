package com.liftstrong.data.repository.impl

import com.liftstrong.data.local.dao.ExerciseDao
import com.liftstrong.data.local.dao.WorkoutTemplateDao
import com.liftstrong.data.local.entity.TemplateExerciseEntity
import com.liftstrong.data.local.entity.TemplateSetEntity
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.local.entity.WorkoutTemplateEntity
import com.liftstrong.data.repository.WorkoutRepository
import com.liftstrong.data.repository.WorkoutTemplateRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Implementation of the WorkoutTemplateRepository interface.
 */
class WorkoutTemplateRepositoryImpl @Inject constructor(
    private val workoutTemplateDao: WorkoutTemplateDao,
    private val exerciseDao: ExerciseDao,
    private val workoutRepository: WorkoutRepository
) : WorkoutTemplateRepository {
    
    override suspend fun getWorkoutTemplateById(id: Int): WorkoutTemplateEntity? {
        return workoutTemplateDao.getWorkoutTemplateById(id)
    }
    
    override fun getWorkoutTemplatesForUser(userId: Int): Flow<List<WorkoutTemplateEntity>> {
        return workoutTemplateDao.getWorkoutTemplatesForUser(userId)
    }
    
    override suspend fun getTemplateExercisesForTemplate(templateId: Int): List<TemplateExerciseEntity> {
        return workoutTemplateDao.getTemplateExercisesForTemplate(templateId)
    }
    
    override suspend fun getTemplateSetsForTemplateExercise(templateExerciseId: Int): List<TemplateSetEntity> {
        return workoutTemplateDao.getTemplateSetsForTemplateExercise(templateExerciseId)
    }
    
    override suspend fun createWorkoutTemplate(userId: Int, name: String, description: String?): Long {
        val template = WorkoutTemplateEntity(
            userId = userId,
            name = name,
            description = description
        )
        return workoutTemplateDao.insertWorkoutTemplate(template)
    }
    
    override suspend fun addExerciseToTemplate(templateId: Int, exerciseId: Int, order: Int): Long {
        val templateExercise = TemplateExerciseEntity(
            templateId = templateId,
            exerciseId = exerciseId,
            order = order
        )
        return workoutTemplateDao.insertTemplateExercise(templateExercise)
    }
    
    override suspend fun addSetToTemplateExercise(
        templateExerciseId: Int,
        weight: Float?,
        reps: Int,
        restTime: Int?
    ): Long {
        val templateSet = TemplateSetEntity(
            templateExerciseId = templateExerciseId,
            weight = weight,
            reps = reps,
            restTime = restTime
        )
        return workoutTemplateDao.insertTemplateSet(templateSet)
    }
    
    override suspend fun updateWorkoutTemplate(template: WorkoutTemplateEntity) {
        workoutTemplateDao.updateWorkoutTemplate(template)
    }
    
    override suspend fun deleteWorkoutTemplateById(id: Int) {
        workoutTemplateDao.deleteWorkoutTemplateById(id)
    }
    
    override suspend fun deleteTemplateExerciseById(id: Int) {
        workoutTemplateDao.deleteTemplateExerciseById(id)
    }
    
    override suspend fun deleteTemplateSetById(id: Int) {
        workoutTemplateDao.deleteTemplateSetById(id)
    }
    
    override suspend fun getExerciseCountForTemplate(templateId: Int): Int {
        return workoutTemplateDao.getExerciseCountForTemplate(templateId)
    }
    
    override suspend fun createWorkoutFromTemplate(templateId: Int, userId: Int): Long {
        // Get the template
        val template = workoutTemplateDao.getWorkoutTemplateById(templateId)
            ?: throw IllegalArgumentException("Template not found")
        
        // Create a new workout
        val workoutId = workoutRepository.createWorkout(
            userId = userId,
            name = template.name,
            date = LocalDateTime.now()
        )
        
        // Get all exercises for the template
        val templateExercises = workoutTemplateDao.getTemplateExercisesForTemplate(templateId)
        
        // Add each exercise to the workout
        for (templateExercise in templateExercises) {
            val workoutExerciseId = workoutRepository.addExerciseToWorkout(
                workoutId = workoutId.toInt(),
                exerciseId = templateExercise.exerciseId,
                order = templateExercise.order
            )
            
            // Get all sets for the template exercise
            val templateSets = workoutTemplateDao.getTemplateSetsForTemplateExercise(templateExercise.id)
            
            // Add each set to the workout exercise
            for (templateSet in templateSets) {
                workoutRepository.addSetToWorkoutExercise(
                    workoutExerciseId = workoutExerciseId.toInt(),
                    weight = templateSet.weight ?: 0f,
                    reps = templateSet.reps,
                    restTime = templateSet.restTime
                )
            }
        }
        
        return workoutId
    }
}
