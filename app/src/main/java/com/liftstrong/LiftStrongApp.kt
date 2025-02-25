package com.liftstrong

import android.app.Application
import com.liftstrong.data.repository.ExerciseRepository
import com.liftstrong.data.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Application class for LiftStrong.
 * 
 * This class serves as the entry point for the application and is annotated with
 * @HiltAndroidApp to enable dependency injection with Hilt.
 */
@HiltAndroidApp
class LiftStrongApp : Application() {
    
    @Inject
    lateinit var exerciseRepository: ExerciseRepository
    
    @Inject
    lateinit var userRepository: UserRepository
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize the database with default data
        initializeDatabase()
    }
    
    /**
     * Initialize the database with default data.
     */
    private fun initializeDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            // Create a default user if none exists
            userRepository.getOrCreateDefaultUser()
            
            // Populate the database with default exercises
            exerciseRepository.populateDefaultExercises()
        }
    }
}
