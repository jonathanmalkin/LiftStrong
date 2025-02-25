package com.liftstrong.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.liftstrong.databinding.FragmentRoutinesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Routines screen.
 * 
 * This fragment displays a list of workout routines and allows the user to create new ones.
 */
@AndroidEntryPoint
class RoutinesFragment : Fragment() {
    
    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RoutinesViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup UI components and observe ViewModel
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Setup RecyclerView and FAB
        binding.fabAddRoutine.setOnClickListener {
            findNavController().navigate(
                RoutinesFragmentDirections.actionRoutinesToRoutineDetail()
            )
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
