package com.liftstrong.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.liftstrong.databinding.FragmentWorkoutHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Workout History screen.
 * 
 * This fragment displays a list of past workouts and allows the user to view their details.
 */
@AndroidEntryPoint
class WorkoutHistoryFragment : Fragment() {
    
    private var _binding: FragmentWorkoutHistoryBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: WorkoutHistoryViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup UI components and observe ViewModel
        setupUI()
        observeViewModel()
        
        // Load workout history
        viewModel.loadWorkoutHistory()
    }
    
    private fun setupUI() {
        // Setup RecyclerView
    }
    
    private fun observeViewModel() {
        // Observe LiveData from ViewModel
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            // Update UI with workouts
            binding.textEmpty.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
        }
    }
    
    private fun navigateToWorkoutDetail(workoutId: Int) {
        val action = WorkoutHistoryFragmentDirections.actionWorkoutHistoryToWorkoutDetail(workoutId)
        findNavController().navigate(action)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
