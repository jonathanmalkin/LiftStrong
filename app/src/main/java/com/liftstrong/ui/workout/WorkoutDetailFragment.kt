package com.liftstrong.ui.workout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.liftstrong.R
import com.liftstrong.databinding.FragmentWorkoutDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Workout Detail screen.
 * 
 * This fragment displays the details of a workout and allows the user to track their sets.
 */
@AndroidEntryPoint
class WorkoutDetailFragment : Fragment() {
    
    private var _binding: FragmentWorkoutDetailBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: WorkoutDetailViewModel by viewModels()
    private val args: WorkoutDetailFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load the workout if editing an existing one
        if (args.workoutId != -1) {
            viewModel.loadWorkout(args.workoutId)
        } else {
            // Create a new workout
            viewModel.createNewWorkout()
        }
        
        // Setup UI components and observe ViewModel
        setupUI()
        observeViewModel()
    }
    
    private lateinit var workoutExerciseAdapter: WorkoutExerciseAdapter
    
    private fun setupUI() {
        // Setup RecyclerView
        workoutExerciseAdapter = WorkoutExerciseAdapter(
            onAddSetClicked = { workoutExercise ->
                showAddSetDialog(workoutExercise.id)
            },
            onRemoveExerciseClicked = { workoutExercise ->
                viewModel.removeWorkoutExercise(workoutExercise.id)
            },
            onRemoveSetClicked = { setId ->
                viewModel.removeWorkoutSet(setId)
            }
        )
        binding.recyclerExercises.adapter = workoutExerciseAdapter
        
        // Setup UI components
        binding.buttonAddExercise.setOnClickListener {
            // Navigate to exercise selection
            val workoutId = viewModel.workout.value?.id ?: args.workoutId
            if (workoutId != -1) {
                findNavController().navigate(
                    WorkoutDetailFragmentDirections.actionWorkoutDetailToExerciseSelection(workoutId)
                )
            }
        }
        
        binding.buttonFinishWorkout.setOnClickListener {
            viewModel.finishWorkout()
            findNavController().navigateUp()
        }
    }
    
    private fun observeViewModel() {
        // Observe workout
        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            binding.textTitle.text = workout.name
            binding.textDate.text = workout.date.toString() // TODO: Format date
        }
        
        // Observe workout exercises
        viewModel.workoutExercises.observe(viewLifecycleOwner) { workoutExercises ->
            workoutExerciseAdapter.submitList(workoutExercises)
            binding.textEmpty.visibility = if (workoutExercises.isEmpty()) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }
    }
    
    private fun showAddSetDialog(workoutExerciseId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_set, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        // Get references to views
        val weightEditText = dialogView.findViewById<TextInputEditText>(R.id.edit_weight)
        val repsEditText = dialogView.findViewById<TextInputEditText>(R.id.edit_reps)
        val rpeEditText = dialogView.findViewById<TextInputEditText>(R.id.edit_rpe)
        val restTimeEditText = dialogView.findViewById<TextInputEditText>(R.id.edit_rest_time)
        val notesEditText = dialogView.findViewById<TextInputEditText>(R.id.edit_notes)
        val saveButton = dialogView.findViewById<Button>(R.id.button_save)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)
        
        // Set up button click listeners
        saveButton.setOnClickListener {
            // Get values from input fields
            val weight = weightEditText.text.toString().toFloatOrNull() ?: 0f
            val reps = repsEditText.text.toString().toIntOrNull() ?: 0
            val rpe = rpeEditText.text.toString().toIntOrNull()
            val restTime = restTimeEditText.text.toString().toIntOrNull()
            val notes = notesEditText.text.toString().takeIf { it.isNotEmpty() }
            
            // Add the set
            viewModel.addSet(
                workoutExerciseId = workoutExerciseId,
                weight = weight,
                reps = reps,
                rpe = rpe,
                restTime = restTime,
                notes = notes
            )
            
            // Dismiss the dialog
            dialog.dismiss()
        }
        
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
