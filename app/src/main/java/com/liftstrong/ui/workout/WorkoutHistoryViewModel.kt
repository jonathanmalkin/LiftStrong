package com.liftstrong.ui.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.repository.UserRepository
import com.liftstrong.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Workout History screen.
 * 
 * This ViewModel handles the business logic for the WorkoutHistoryFragment,
 * including loading and filtering workout history.
 */
@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _workouts = MutableLiveData<List<WorkoutEntity>>()
    val workouts: LiveData<List<WorkoutEntity>> = _workouts
    
    /**
     * Load workout history for the current user.
     */
    fun loadWorkoutHistory() {
        viewModelScope.launch {
            val user = userRepository.getOrCreateDefaultUser()
            
            workoutRepository.getWorkoutsForUser(user.id).collect { workoutList ->
                _workouts.postValue(workoutList)
            }
        }
    }
    
    /**
     * Delete a workout by ID.
     * 
     * @param workoutId The ID of the workout to delete.
     */
    fun deleteWorkout(workoutId: Int) {
        viewModelScope.launch {
            workoutRepository.deleteWorkoutById(workoutId)
        }
    }
}
