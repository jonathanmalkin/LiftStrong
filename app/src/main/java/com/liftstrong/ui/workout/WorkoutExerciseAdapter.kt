package com.liftstrong.ui.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.data.local.entity.WorkoutExerciseEntity
import com.liftstrong.databinding.ItemWorkoutExerciseBinding

/**
 * Adapter for displaying workout exercises in a RecyclerView.
 * 
 * @param onAddSetClicked Callback for when the "Add Set" button is clicked.
 * @param onRemoveExerciseClicked Callback for when the remove button is clicked.
 * @param onRemoveSetClicked Callback for when a set's remove button is clicked.
 */
class WorkoutExerciseAdapter(
    private val onAddSetClicked: (WorkoutExerciseEntity) -> Unit,
    private val onRemoveExerciseClicked: (WorkoutExerciseEntity) -> Unit,
    private val onRemoveSetClicked: (Int) -> Unit
) : ListAdapter<WorkoutExerciseWithExercise, WorkoutExerciseAdapter.WorkoutExerciseViewHolder>(WorkoutExerciseDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutExerciseViewHolder {
        val binding = ItemWorkoutExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutExerciseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: WorkoutExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class WorkoutExerciseViewHolder(
        private val binding: ItemWorkoutExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val setAdapter = WorkoutSetAdapter { setId ->
            // Handle set removal
            onRemoveSetClicked(setId)
        }
        
        init {
            binding.recyclerSets.adapter = setAdapter
            
            binding.buttonAddSet.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAddSetClicked(getItem(position).workoutExercise)
                }
            }
            
            binding.buttonRemove.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRemoveExerciseClicked(getItem(position).workoutExercise)
                }
            }
        }
        
        fun bind(item: WorkoutExerciseWithExercise) {
            binding.textExerciseName.text = item.exercise.name
            binding.textMuscleGroups.text = item.exercise.muscleGroups
            
            // Update the set adapter with the sets for this exercise
            setAdapter.submitList(item.sets)
        }
    }
    
    /**
     * DiffUtil callback for calculating the difference between two WorkoutExerciseWithExercise lists.
     */
    private class WorkoutExerciseDiffCallback : DiffUtil.ItemCallback<WorkoutExerciseWithExercise>() {
        override fun areItemsTheSame(oldItem: WorkoutExerciseWithExercise, newItem: WorkoutExerciseWithExercise): Boolean {
            return oldItem.workoutExercise.id == newItem.workoutExercise.id
        }
        
        override fun areContentsTheSame(oldItem: WorkoutExerciseWithExercise, newItem: WorkoutExerciseWithExercise): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Data class that combines a WorkoutExerciseEntity with its corresponding ExerciseEntity and sets.
 */
data class WorkoutExerciseWithExercise(
    val workoutExercise: WorkoutExerciseEntity,
    val exercise: ExerciseEntity,
    val sets: List<WorkoutSetWithNumber> = emptyList()
)
