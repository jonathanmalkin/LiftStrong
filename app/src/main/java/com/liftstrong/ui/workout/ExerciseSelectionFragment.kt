package com.liftstrong.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.liftstrong.data.local.entity.ExerciseEntity
import com.liftstrong.databinding.FragmentExerciseSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for selecting exercises to add to a workout.
 */
@AndroidEntryPoint
class ExerciseSelectionFragment : Fragment() {
    
    private var _binding: FragmentExerciseSelectionBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ExerciseSelectionViewModel by viewModels()
    private val args: ExerciseSelectionFragmentArgs by navArgs()
    
    private lateinit var exerciseAdapter: ExerciseSelectionAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupSearchView()
        setupTabLayout()
        observeViewModel()
        
        // Load all exercises initially
        viewModel.loadExercises()
    }
    
    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseSelectionAdapter { exercise ->
            // Add the selected exercise to the workout
            viewModel.addExerciseToWorkout(args.workoutId, exercise.id)
            // Navigate back to the workout detail screen
            findNavController().navigateUp()
        }
        
        binding.recyclerExercises.adapter = exerciseAdapter
    }
    
    private fun setupSearchView() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let {
                    if (it.isNotEmpty()) {
                        viewModel.searchExercises(it)
                    } else {
                        viewModel.loadExercises()
                    }
                }
            }
            
            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })
    }
    
    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.loadExercises() // All
                    1 -> viewModel.loadExercisesByMuscleGroup("Chest") // Chest
                    2 -> viewModel.loadExercisesByMuscleGroup("Back") // Back
                    3 -> viewModel.loadExercisesByMuscleGroup("Legs") // Legs
                    4 -> viewModel.loadExercisesByMuscleGroup("Shoulders") // Shoulders
                    5 -> viewModel.loadExercisesByMuscleGroup("Arms") // Arms
                    6 -> viewModel.loadExercisesByMuscleGroup("Core") // Core
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed
            }
            
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed
            }
        })
    }
    
    private fun observeViewModel() {
        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.submitList(exercises)
            binding.textEmpty.isVisible = exercises.isEmpty()
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
