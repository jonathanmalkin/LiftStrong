package com.liftstrong.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    
    private fun setupUI() {
        // Setup UI components
        binding.buttonAddExercise.setOnClickListener {
            // Navigate to exercise selection
        }
        
        binding.buttonFinishWorkout.setOnClickListener {
            viewModel.finishWorkout()
            findNavController().navigateUp()
        }
    }
    
    private fun observeViewModel() {
        // Observe LiveData from ViewModel
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
