package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity representing an exercise within a workout template in the database.
 * 
 * This entity links a workout template to an exercise and stores information about the exercise
 * within the context of the template, such as the order.
 */
@Entity(
    tableName = "template_exercise",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("templateId"),
        Index("exerciseId")
    ]
)
data class TemplateExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the workout template this exercise belongs to.
     */
    val templateId: Int,
    
    /**
     * The ID of the exercise.
     */
    val exerciseId: Int,
    
    /**
     * The order of the exercise within the template.
     */
    val order: Int
)
