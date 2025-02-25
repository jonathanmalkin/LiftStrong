package com.liftstrong.ui.routines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftstrong.data.local.entity.TemplateExerciseEntity
import com.liftstrong.data.local.entity.WorkoutTemplateEntity
import com.liftstrong.data.repository.ExerciseRepository
import com.liftstrong.data.repository.UserRepository
import com.liftstrong.data.repository.WorkoutTemplateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Routine Detail screen.
 * 
 * This ViewModel handles the business logic for the RoutineDetailFragment,
 * including loading, saving, and updating workout routines.
 */
@HiltViewModel
class RoutineDetailViewModel @Inject constructor(
    private val workoutTemplateRepository: WorkoutTemplateRepository,
    private val exerciseRepository: ExerciseRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _routine = MutableLiveData<WorkoutTemplateEntity>()
    val routine: LiveData<WorkoutTemplateEntity> = _routine
    
    private val _exercises = MutableLiveData<List<TemplateExerciseEntity>>()
    val exercises: LiveData<List<TemplateExerciseEntity>> = _exercises
    
    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved
    
    private var routineId: Int = -1
    
    /**
     * Load a routine by ID.
     * 
     * @param id The ID of the routine to load.
     */
    fun loadRoutine(id: Int) {
        viewModelScope.launch {
            routineId = id
            val routine = workoutTemplateRepository.getWorkoutTemplateById(id)
            routine?.let {
                _routine.postValue(it)
                loadExercises(id)
            }
        }
    }
    
    /**
     * Load exercises for a routine.
     * 
     * @param routineId The ID of the routine.
     */
    private fun loadExercises(routineId: Int) {
        viewModelScope.launch {
            val exercises = workoutTemplateRepository.getTemplateExercisesForTemplate(routineId)
            _exercises.postValue(exercises)
        }
    }
    
    /**
     * Save a routine.
     * 
     * @param name The name of the routine.
     * @param description The description of the routine.
     */
    fun saveRoutine(name: String, description: String) {
        viewModelScope.launch {
            val user = userRepository.getOrCreateDefaultUser()
            
            if (routineId == -1) {
                // Create a new routine
                routineId = workoutTemplateRepository.createWorkoutTemplate(
                    userId = user.id,
                    name = name,
                    description = description
                ).toInt()
            } else {
                // Update an existing routine
                val currentRoutine = _routine.value
                if (currentRoutine != null) {
                    val updatedRoutine = currentRoutine.copy(
                        name = name,
                        description = description
                    )
                    workoutTemplateRepository.updateWorkoutTemplate(updatedRoutine)
                }
            }
            
            _isSaved.postValue(true)
        }
    }
    
    /**
     * Add an exercise to the routine.
     * 
     * @param exerciseId The ID of the exercise to add.
     */
    fun addExercise(exerciseId: Int) {
        viewModelScope.launch {
            if (routineId != -1) {
                val order = (_exercises.value?.size ?: 0) + 1
                workoutTemplateRepository.addExerciseToTemplate(
                    templateId = routineId,
                    exerciseId = exerciseId,
                    order = order
                )
                loadExercises(routineId)
            }
        }
    }
    
    /**
     * Remove an exercise from the routine.
     * 
     * @param templateExerciseId The ID of the template exercise to remove.
     */
    fun removeExercise(templateExerciseId: Int) {
        viewModelScope.launch {
            workoutTemplateRepository.deleteTemplateExerciseById(templateExerciseId)
            loadExercises(routineId)
        }
    }
}
