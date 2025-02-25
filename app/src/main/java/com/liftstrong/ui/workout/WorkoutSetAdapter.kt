package com.liftstrong.ui.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftstrong.data.local.entity.WorkoutSetEntity
import com.liftstrong.databinding.ItemWorkoutSetBinding

/**
 * Adapter for displaying workout sets in a RecyclerView.
 * 
 * @param onRemoveSetClicked Callback for when the remove button is clicked.
 */
class WorkoutSetAdapter(
    private val onRemoveSetClicked: (Int) -> Unit
) : ListAdapter<WorkoutSetWithNumber, WorkoutSetAdapter.WorkoutSetViewHolder>(WorkoutSetDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSetViewHolder {
        val binding = ItemWorkoutSetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutSetViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: WorkoutSetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class WorkoutSetViewHolder(
        private val binding: ItemWorkoutSetBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.buttonRemoveSet.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRemoveSetClicked(getItem(position).set.id)
                }
            }
        }
        
        fun bind(item: WorkoutSetWithNumber) {
            binding.textSetNumber.text = "Set ${item.setNumber}"
            binding.textWeight.text = "${item.set.weight} lbs" // TODO: Use user's preferred unit
            binding.textReps.text = "${item.set.reps} reps"
            
            // Show RPE if available
            if (item.set.rpe != null) {
                binding.textRpe.text = "RPE ${item.set.rpe}"
                binding.textRpe.visibility = android.view.View.VISIBLE
            } else {
                binding.textRpe.visibility = android.view.View.GONE
            }
        }
    }
    
    /**
     * DiffUtil callback for calculating the difference between two WorkoutSetWithNumber lists.
     */
    private class WorkoutSetDiffCallback : DiffUtil.ItemCallback<WorkoutSetWithNumber>() {
        override fun areItemsTheSame(oldItem: WorkoutSetWithNumber, newItem: WorkoutSetWithNumber): Boolean {
            return oldItem.set.id == newItem.set.id
        }
        
        override fun areContentsTheSame(oldItem: WorkoutSetWithNumber, newItem: WorkoutSetWithNumber): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Data class that combines a WorkoutSetEntity with its set number.
 */
data class WorkoutSetWithNumber(
    val set: WorkoutSetEntity,
    val setNumber: Int
)
