package com.liftstrong.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity representing a set within a template exercise in the database.
 * 
 * This entity stores information about a set within a template exercise, including weight, reps, and rest time.
 */
@Entity(
    tableName = "template_set",
    foreignKeys = [
        ForeignKey(
            entity = TemplateExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("templateExerciseId")
    ]
)
data class TemplateSetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * The ID of the template exercise this set belongs to.
     */
    val templateExerciseId: Int,
    
    /**
     * The weight for the set.
     */
    val weight: Float?,
    
    /**
     * The number of repetitions for the set.
     */
    val reps: Int,
    
    /**
     * The rest time after the set, in seconds.
     */
    val restTime: Int?
)
