package com.liftstrong.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.liftstrong.databinding.FragmentRoutineDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Routine Detail screen.
 * 
 * This fragment displays the details of a workout routine and allows the user to edit it.
 */
@AndroidEntryPoint
class RoutineDetailFragment : Fragment() {
    
    private var _binding: FragmentRoutineDetailBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RoutineDetailViewModel by viewModels()
    private val args: RoutineDetailFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load the routine if editing an existing one
        if (args.routineId != -1) {
            viewModel.loadRoutine(args.routineId)
        }
        
        // Setup UI components and observe ViewModel
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Setup UI components
        binding.buttonSave.setOnClickListener {
            saveRoutine()
        }
    }
    
    private fun observeViewModel() {
        // Observe LiveData from ViewModel
    }
    
    private fun saveRoutine() {
        val name = binding.editTextRoutineName.text.toString()
        val description = binding.editTextRoutineDescription.text.toString()
        
        if (name.isNotBlank()) {
            viewModel.saveRoutine(name, description)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
