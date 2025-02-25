package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a user in the database.
 * 
 * This entity stores user settings and preferences.
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The user's name.
     */
    val name: String,
    
    /**
     * The user's preferred weight unit (e.g., "lbs" or "kg").
     */
    val weightUnit: String,
    
    /**
     * The user's default rest time in seconds.
     */
    val defaultRestTime: Int
)
