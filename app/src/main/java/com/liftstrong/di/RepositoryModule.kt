package com.liftstrong.di

import com.liftstrong.data.local.LiftStrongDatabase
import com.liftstrong.data.repository.ExerciseRepository
import com.liftstrong.data.repository.UserRepository
import com.liftstrong.data.repository.WorkoutRepository
import com.liftstrong.data.repository.WorkoutTemplateRepository
import com.liftstrong.data.repository.impl.ExerciseRepositoryImpl
import com.liftstrong.data.repository.impl.UserRepositoryImpl
import com.liftstrong.data.repository.impl.WorkoutRepositoryImpl
import com.liftstrong.data.repository.impl.WorkoutTemplateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    /**
     * Provides the UserRepository implementation.
     */
    @Provides
    @Singleton
    fun provideUserRepository(database: LiftStrongDatabase): UserRepository {
        return UserRepositoryImpl(database.userDao())
    }
    
    /**
     * Provides the ExerciseRepository implementation.
     */
    @Provides
    @Singleton
    fun provideExerciseRepository(database: LiftStrongDatabase): ExerciseRepository {
        return ExerciseRepositoryImpl(database.exerciseDao())
    }
    
    /**
     * Provides the WorkoutRepository implementation.
     */
    @Provides
    @Singleton
    fun provideWorkoutRepository(database: LiftStrongDatabase): WorkoutRepository {
        return WorkoutRepositoryImpl(
            workoutDao = database.workoutDao(),
            exerciseDao = database.exerciseDao()
        )
    }
    
    /**
     * Provides the WorkoutTemplateRepository implementation.
     */
    @Provides
    @Singleton
    fun provideWorkoutTemplateRepository(
        database: LiftStrongDatabase,
        workoutRepository: WorkoutRepository
    ): WorkoutTemplateRepository {
        return WorkoutTemplateRepositoryImpl(
            workoutTemplateDao = database.workoutTemplateDao(),
            exerciseDao = database.exerciseDao(),
            workoutRepository = workoutRepository
        )
    }
}
