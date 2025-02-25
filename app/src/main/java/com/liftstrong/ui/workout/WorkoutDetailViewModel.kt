package com.liftstrong.ui.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.repository.UserRepository
import com.liftstrong.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ViewModel for the Workout Detail screen.
 * 
 * This ViewModel handles the business logic for the WorkoutDetailFragment,
 * including creating, loading, and updating workouts.
 */
@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _workout = MutableLiveData<WorkoutEntity>()
    val workout: LiveData<WorkoutEntity> = _workout
    
    private val _exercises = MutableLiveData<List<WorkoutExerciseEntity>>()
    val exercises: LiveData<List<WorkoutExerciseEntity>> = _exercises
    
    private var workoutId: Int = -1
    private var startTime: LocalDateTime = LocalDateTime.now()
    
    /**
     * Create a new workout.
     */
    fun createNewWorkout() {
        viewModelScope.launch {
            val user = userRepository.getOrCreateDefaultUser()
            
            workoutId = workoutRepository.createWorkout(
                userId = user.id,
                name = "Workout ${LocalDateTime.now()}",
                date = startTime
            ).toInt()
            
            loadWorkout(workoutId)
        }
    }
    
    /**
     * Load a workout by ID.
     * 
     * @param id The ID of the workout to load.
     */
    fun loadWorkout(id: Int) {
        viewModelScope.launch {
            workoutId = id
            val workout = workoutRepository.getWorkoutById(id)
            workout?.let {
                _workout.postValue(it)
                loadExercises(id)
            }
        }
    }
    
    /**
     * Load exercises for a workout.
     * 
     * @param workoutId The ID of the workout.
     */
    private fun loadExercises(workoutId: Int) {
        viewModelScope.launch {
            val exercises = workoutRepository.getWorkoutExercisesForWorkout(workoutId)
            _exercises.postValue(exercises)
        }
    }
    
    /**
     * Add an exercise to the workout.
     * 
     * @param exerciseId The ID of the exercise to add.
     */
    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            if (workoutId != -1) {
                val order = (_exercises.value?.size ?: 0) + 1
                workoutRepository.addExerciseToWorkout(
                    workoutId = workoutId,
                    exerciseId = exerciseId,
                    order = order
                )
                loadExercises(workoutId)
            }
        }
    }
    
    /**
     * Add a set to a workout exercise.
     * 
     * @param workoutExerciseId The ID of the workout exercise.
     * @param weight The weight used for the set.
     * @param reps The number of repetitions performed.
     * @param rpe Optional Rate of Perceived Exertion for the set.
     * @param restTime Optional rest time after the set.
     * @param notes Optional notes for the set.
     */
    fun addSet(
        workoutExerciseId: Int,
        weight: Float,
        reps: Int,
        rpe: Int? = null,
        restTime: Int? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            workoutRepository.addSetToWorkoutExercise(
                workoutExerciseId = workoutExerciseId,
                weight = weight,
                reps = reps,
                rpe = rpe,
                restTime = restTime,
                notes = notes
            )
        }
    }
    
    /**
     * Finish the workout by setting its duration.
     */
    fun finishWorkout() {
        viewModelScope.launch {
            if (workoutId != -1) {
                val endTime = LocalDateTime.now()
                val durationMinutes = java.time.Duration.between(startTime, endTime).toMinutes().toInt()
                workoutRepository.completeWorkout(workoutId, durationMinutes)
            }
        }
    }
}
