package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity representing a workout template in the database.
 * 
 * This entity stores information about a workout template, which is a predefined workout
 * that can be used to create new workouts.
 */
@Entity(
    tableName = "workout_template",
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
data class WorkoutTemplateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the user who created the template.
     */
    val userId: Int,
    
    /**
     * The name of the template.
     */
    val name: String,
    
    /**
     * The description of the template.
     */
    val description: String?
)
