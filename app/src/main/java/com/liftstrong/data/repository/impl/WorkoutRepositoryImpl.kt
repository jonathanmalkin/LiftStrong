package com.liftstrong.data.repository.impl

import com.liftstrong.data.local.dao.ExerciseDao
import com.liftstrong.data.local.dao.WorkoutDao
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.local.entity.WorkoutSetEntity
import com.liftstrong.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Implementation of the WorkoutRepository interface.
 */
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) : WorkoutRepository {
    
    override suspend fun getWorkoutById(id: Int): WorkoutEntity? {
        return workoutDao.getWorkoutById(id)
    }
    
    override fun getWorkoutsForUser(userId: Int): Flow<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsForUser(userId)
    }
    
    override suspend fun getWorkoutExercisesForWorkout(workoutId: Int): List<WorkoutExerciseEntity> {
        return workoutDao.getWorkoutExercisesForWorkout(workoutId)
    }
    
    override suspend fun getWorkoutSetsForWorkoutExercise(workoutExerciseId: Int): List<WorkoutSetEntity> {
        return workoutDao.getWorkoutSetsForWorkoutExercise(workoutExerciseId)
    }
    
    override fun getWorkoutsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsBetweenDates(startDate, endDate)
    }
    
    override suspend fun createWorkout(userId: Int, name: String, date: LocalDateTime, notes: String?): Long {
        val workout = WorkoutEntity(
            userId = userId,
            name = name,
            date = date,
            duration = 0, // Will be updated when the workout is completed
            notes = notes
        )
        return workoutDao.insertWorkout(workout)
    }
    
    override suspend fun addExerciseToWorkout(workoutId: Int, exerciseId: Int, order: Int, notes: String?): Long {
        val workoutExercise = WorkoutExerciseEntity(
            workoutId = workoutId,
            exerciseId = exerciseId,
            order = order,
            notes = notes
        )
        return workoutDao.insertWorkoutExercise(workoutExercise)
    }
    
    override suspend fun addSetToWorkoutExercise(
        workoutExerciseId: Int,
        weight: Float,
        reps: Int,
        rpe: Int?,
        restTime: Int?,
        notes: String?
    ): Long {
        val workoutSet = WorkoutSetEntity(
            workoutExerciseId = workoutExerciseId,
            weight = weight,
            reps = reps,
            rpe = rpe,
            restTime = restTime,
            notes = notes
        )
        return workoutDao.insertWorkoutSet(workoutSet)
    }
    
    override suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }
    
    override suspend fun deleteWorkoutById(id: Int) {
        workoutDao.deleteWorkoutById(id)
    }
    
    override suspend fun deleteWorkoutExerciseById(id: Int) {
        workoutDao.deleteWorkoutExerciseById(id)
    }
    
    override suspend fun deleteWorkoutSetById(id: Int) {
        workoutDao.deleteWorkoutSetById(id)
    }
    
    override suspend fun getLatestWorkoutForExercise(exerciseId: Int): WorkoutEntity? {
        return workoutDao.getLatestWorkoutForExercise(exerciseId)
    }
    
    override suspend fun getLatestWorkoutSetsForExercise(exerciseId: Int): List<WorkoutSetEntity> {
        return workoutDao.getLatestWorkoutSetsForExercise(exerciseId)
    }
    
    override suspend fun completeWorkout(workoutId: Int, duration: Int) {
        val workout = workoutDao.getWorkoutById(workoutId)
        if (workout != null) {
            val updatedWorkout = workout.copy(duration = duration)
            workoutDao.updateWorkout(updatedWorkout)
        }
    }
}
