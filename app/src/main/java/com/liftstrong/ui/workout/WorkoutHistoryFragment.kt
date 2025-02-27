package com.liftstrong.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.liftstrong.R
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
        val adapter = WorkoutAdapter { workoutId ->
            navigateToWorkoutDetail(workoutId)
        }
        
        binding.recyclerWorkouts.adapter = adapter
        binding.recyclerWorkouts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerWorkouts.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }
    
    private fun observeViewModel() {
        // Observe LiveData from ViewModel
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            // Update UI with workouts
            (binding.recyclerWorkouts.adapter as? WorkoutAdapter)?.submitList(workouts)
            binding.textEmpty.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
        }
    }
    
    private fun navigateToWorkoutDetail(workoutId: Int) {
        val bundle = Bundle().apply {
            putInt("workoutId", workoutId)
        }
        findNavController().navigate(R.id.action_workout_history_to_workout_detail, bundle)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
