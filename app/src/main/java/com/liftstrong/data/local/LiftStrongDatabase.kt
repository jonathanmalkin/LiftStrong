package com.liftstrong.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liftstrong.data.local.dao.ExerciseDao
import com.liftstrong.data.local.dao.UserDao
import com.liftstrong.data.local.dao.WorkoutDao
import com.liftstrong.data.local.dao.WorkoutTemplateDao
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.data.local.entity.TemplateExerciseEntity
import com.liftstrong.data.local.entity.TemplateSetEntity
import com.liftstrong.data.local.entity.UserEntity
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.data.local.entity.WorkoutSetEntity
import com.liftstrong.data.local.entity.WorkoutTemplateEntity

/**
 * Room database for the LiftStrong app.
 * 
 * This database stores all the data for the app, including exercises, workouts, and user settings.
 */
@Database(
    entities = [
        UserEntity::class,
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        WorkoutSetEntity::class,
        WorkoutTemplateEntity::class,
        TemplateExerciseEntity::class,
        TemplateSetEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LiftStrongDatabase : RoomDatabase() {
    
    /**
     * Returns the DAO for user operations.
     */
    abstract fun userDao(): UserDao
    
    /**
     * Returns the DAO for exercise operations.
     */
    abstract fun exerciseDao(): ExerciseDao
    
    /**
     * Returns the DAO for workout operations.
     */
    abstract fun workoutDao(): WorkoutDao
    
    /**
     * Returns the DAO for workout template operations.
     */
    abstract fun workoutTemplateDao(): WorkoutTemplateDao
}
