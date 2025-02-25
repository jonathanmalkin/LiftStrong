package com.liftstrong.ui.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.data.repository.ExerciseRepository
import com.liftstrong.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Exercise Selection screen.
 * 
 * This ViewModel handles the business logic for the ExerciseSelectionFragment,
 * including loading exercises, filtering by muscle group, and searching.
 */
@HiltViewModel
class ExerciseSelectionViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    
    private val _exercises = MutableLiveData<List<ExerciseEntity>>()
    val exercises: LiveData<List<ExerciseEntity>> = _exercises
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Load all exercises.
     */
    fun loadExercises() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Ensure the database has default exercises
                exerciseRepository.populateDefaultExercises()
                
                // Get all exercises
                exerciseRepository.getAllExercises().collect { exerciseList ->
                    _exercises.value = exerciseList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle error
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Load exercises by muscle group.
     * 
     * @param muscleGroup The muscle group to filter by.
     */
    fun loadExercisesByMuscleGroup(muscleGroup: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                exerciseRepository.getExercisesByMuscleGroup(muscleGroup).collect { exerciseList ->
                    _exercises.value = exerciseList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle error
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Search exercises by name.
     * 
     * @param query The search query.
     */
    fun searchExercises(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                exerciseRepository.searchExercises(query).collect { exerciseList ->
                    _exercises.value = exerciseList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle error
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Add an exercise to a workout.
     * 
     * @param workoutId The ID of the workout.
     * @param exerciseId The ID of the exercise to add.
     */
    fun addExerciseToWorkout(workoutId: Int, exerciseId: Int) {
        viewModelScope.launch {
            try {
                // Get the current number of exercises in the workout to determine the order
                val currentExercises = workoutRepository.getWorkoutExercisesForWorkout(workoutId)
                val order = currentExercises.size + 1
                
                // Add the exercise to the workout
                workoutRepository.addExerciseToWorkout(
                    workoutId = workoutId,
                    exerciseId = exerciseId,
                    order = order
                )
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
