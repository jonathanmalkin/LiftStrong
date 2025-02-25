package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Entity representing a workout in the database.
 * 
 * This entity stores information about a workout session, including date, duration, and notes.
 */
@Entity(
    tableName = "workout",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId")
    ]
)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the user who performed the workout.
     */
    val userId: Int,
    
    /**
     * The name of the workout.
     */
    val name: String,
    
    /**
     * The date and time when the workout was performed.
     */
    val date: LocalDateTime,
    
    /**
     * The duration of the workout in minutes.
     */
    val duration: Int,
    
    /**
     * Notes about the workout.
     */
    val notes: String?
)
