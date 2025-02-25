package com.liftstrong.data.repository.impl

import com.liftstrong.data.local.dao.ExerciseDao
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * Implementation of the ExerciseRepository interface.
 */
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    
    override suspend fun getExerciseById(id: Int): ExerciseEntity? {
        return exerciseDao.getExerciseById(id)
    }
    
    override fun getAllExercises(): Flow<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()
    }
    
    override fun getExercisesByMuscleGroup(muscleGroup: String): Flow<List<ExerciseEntity>> {
        return exerciseDao.getExercisesByMuscleGroup(muscleGroup)
    }
    
    override fun getExercisesByEquipment(equipment: String): Flow<List<ExerciseEntity>> {
        return exerciseDao.getExercisesByEquipment(equipment)
    }
    
    override fun searchExercises(query: String): Flow<List<ExerciseEntity>> {
        return exerciseDao.searchExercises(query)
    }
    
    override suspend fun insertExercise(exercise: ExerciseEntity): Long {
        return exerciseDao.insertExercise(exercise)
    }
    
    override suspend fun insertExercises(exercises: List<ExerciseEntity>): List<Long> {
        return exerciseDao.insertExercises(exercises)
    }
    
    override suspend fun updateExercise(exercise: ExerciseEntity) {
        exerciseDao.updateExercise(exercise)
    }
    
    override suspend fun deleteExerciseById(id: Int) {
        exerciseDao.deleteExerciseById(id)
    }
    
    override suspend fun populateDefaultExercises() {
        // Check if there are already exercises in the database
        val exercises = exerciseDao.getAllExercises().firstOrNull()
        
        // If there are no exercises, populate with default exercises
        if (exercises.isNullOrEmpty()) {
            val defaultExercises = listOf(
                // Chest exercises
                ExerciseEntity(
                    name = "Barbell Bench Press",
                    muscleGroups = "Chest, Triceps, Shoulders",
                    equipment = "Barbell, Bench",
                    instructions = "Lie on a flat bench with feet on the ground. Grip the barbell with hands slightly wider than shoulder-width. Lower the bar to your chest, then press it back up to the starting position.",
                    difficulty = "Intermediate",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Dumbbell Bench Press",
                    muscleGroups = "Chest, Triceps, Shoulders",
                    equipment = "Dumbbells, Bench",
                    instructions = "Lie on a flat bench with feet on the ground. Hold a dumbbell in each hand at chest level. Press the dumbbells up until your arms are extended, then lower them back to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Push-Up",
                    muscleGroups = "Chest, Triceps, Shoulders, Core",
                    equipment = "None",
                    instructions = "Start in a plank position with hands slightly wider than shoulder-width. Lower your body until your chest nearly touches the floor, then push back up to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                
                // Back exercises
                ExerciseEntity(
                    name = "Pull-Up",
                    muscleGroups = "Back, Biceps",
                    equipment = "Pull-Up Bar",
                    instructions = "Hang from a pull-up bar with hands slightly wider than shoulder-width. Pull your body up until your chin is over the bar, then lower back to the starting position.",
                    difficulty = "Intermediate",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Barbell Row",
                    muscleGroups = "Back, Biceps",
                    equipment = "Barbell",
                    instructions = "Bend at the hips with a slight bend in the knees. Grip the barbell with hands shoulder-width apart. Pull the barbell to your lower chest, then lower it back to the starting position.",
                    difficulty = "Intermediate",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Lat Pulldown",
                    muscleGroups = "Back, Biceps",
                    equipment = "Cable Machine",
                    instructions = "Sit at a lat pulldown machine with knees secured under the pads. Grip the bar with hands wider than shoulder-width. Pull the bar down to your upper chest, then slowly release it back to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                
                // Legs exercises
                ExerciseEntity(
                    name = "Barbell Squat",
                    muscleGroups = "Quadriceps, Hamstrings, Glutes",
                    equipment = "Barbell, Squat Rack",
                    instructions = "Place the barbell on your upper back. Stand with feet shoulder-width apart. Bend at the knees and hips to lower your body, then push through your heels to return to the starting position.",
                    difficulty = "Intermediate",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Deadlift",
                    muscleGroups = "Hamstrings, Glutes, Lower Back",
                    equipment = "Barbell",
                    instructions = "Stand with feet hip-width apart and the barbell over your mid-foot. Bend at the hips and knees to grip the barbell. Lift the barbell by extending your hips and knees, then lower it back to the ground.",
                    difficulty = "Advanced",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Leg Press",
                    muscleGroups = "Quadriceps, Hamstrings, Glutes",
                    equipment = "Leg Press Machine",
                    instructions = "Sit in the leg press machine with feet shoulder-width apart on the platform. Release the safety and lower the platform by bending your knees, then push through your heels to extend your legs.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                
                // Shoulders exercises
                ExerciseEntity(
                    name = "Overhead Press",
                    muscleGroups = "Shoulders, Triceps",
                    equipment = "Barbell",
                    instructions = "Stand with feet shoulder-width apart. Hold the barbell at shoulder height with hands slightly wider than shoulder-width. Press the barbell overhead, then lower it back to the starting position.",
                    difficulty = "Intermediate",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Lateral Raise",
                    muscleGroups = "Shoulders",
                    equipment = "Dumbbells",
                    instructions = "Stand with feet shoulder-width apart. Hold a dumbbell in each hand at your sides. Raise the dumbbells out to the sides until they reach shoulder height, then lower them back to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                
                // Arms exercises
                ExerciseEntity(
                    name = "Barbell Curl",
                    muscleGroups = "Biceps",
                    equipment = "Barbell",
                    instructions = "Stand with feet shoulder-width apart. Hold the barbell with hands shoulder-width apart, palms facing forward. Curl the barbell up to your shoulders, then lower it back to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                ),
                ExerciseEntity(
                    name = "Tricep Pushdown",
                    muscleGroups = "Triceps",
                    equipment = "Cable Machine",
                    instructions = "Stand facing a cable machine with a rope attachment at head height. Grip the rope with both hands, palms facing each other. Push the rope down until your arms are fully extended, then slowly return to the starting position.",
                    difficulty = "Beginner",
                    isCustom = false
                )
            )
            
            insertExercises(defaultExercises)
        }
    }
}
