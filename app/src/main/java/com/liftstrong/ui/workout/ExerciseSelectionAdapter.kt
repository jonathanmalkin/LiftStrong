package com.liftstrong.ui.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.databinding.ItemExerciseBinding

/**
 * Adapter for displaying exercises in a RecyclerView.
 * 
 * @param onExerciseClicked Callback for when an exercise is clicked.
 */
class ExerciseSelectionAdapter(
    private val onExerciseClicked: (ExerciseEntity) -> Unit
) : ListAdapter<ExerciseEntity, ExerciseSelectionAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onExerciseClicked(getItem(position))
                }
            }
            
            // Hide the remove button in selection mode
            binding.buttonRemove.visibility = android.view.View.GONE
        }
        
        fun bind(exercise: ExerciseEntity) {
            binding.textExerciseName.text = exercise.name
            binding.textMuscleGroups.text = exercise.muscleGroups
        }
    }
    
    /**
     * DiffUtil callback for calculating the difference between two ExerciseEntity lists.
     */
    private class ExerciseDiffCallback : DiffUtil.ItemCallback<ExerciseEntity>() {
        override fun areItemsTheSame(oldItem: ExerciseEntity, newItem: ExerciseEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ExerciseEntity, newItem: ExerciseEntity): Boolean {
            return oldItem == newItem
        }
    }
}
