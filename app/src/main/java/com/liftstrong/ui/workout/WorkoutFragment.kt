package com.liftstrong.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.liftstrong.R
import com.liftstrong.databinding.FragmentWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Workouts screen.
 * 
 * This fragment displays options to start a new workout or view workout history.
 */
@AndroidEntryPoint
class WorkoutFragment : Fragment() {
    
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: WorkoutViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.buttonStartWorkout.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionWorkoutsToWorkoutDetail()
            )
        }
        
        binding.buttonWorkoutHistory.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionWorkoutsToWorkoutHistory()
            )
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
