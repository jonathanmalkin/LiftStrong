package com.liftstrong.ui.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liftstrong.data.local.entity.WorkoutEntity
import com.liftstrong.databinding.ItemWorkoutBinding
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Adapter for displaying workouts in a RecyclerView.
 *
 * @param onWorkoutClicked Callback for when a workout is clicked.
 */
class WorkoutAdapter(
    private val onWorkoutClicked: (Int) -> Unit
) : ListAdapter<WorkoutEntity, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkoutViewHolder(
        private val binding: ItemWorkoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onWorkoutClicked(getItem(position).id)
                }
            }
        }

        fun bind(workout: WorkoutEntity) {
            binding.textWorkoutName.text = workout.name
            
            // Format the date
            val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
            binding.textDate.text = workout.date.format(dateFormatter)
            
            // Format the duration
            val hours = workout.duration / 60
            val minutes = workout.duration % 60
            binding.textDuration.text = if (hours > 0) {
                "$hours h $minutes min"
            } else {
                "$minutes min"
            }
            
            // Note: The layout doesn't have a notes field, so we're not setting it
        }
    }

    /**
     * DiffUtil callback for calculating the difference between two WorkoutEntity lists.
     */
    private class WorkoutDiffCallback : DiffUtil.ItemCallback<WorkoutEntity>() {
        override fun areItemsTheSame(oldItem: WorkoutEntity, newItem: WorkoutEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutEntity, newItem: WorkoutEntity): Boolean {
            return oldItem == newItem
        }
    }
}
