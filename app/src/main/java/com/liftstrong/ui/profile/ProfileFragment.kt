package com.liftstrong.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.liftstrong.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for the Profile screen.
 * 
 * This fragment displays user settings and preferences.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProfileViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup UI components and observe ViewModel
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Setup UI components
        binding.radioGroupUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonPounds.id -> viewModel.setWeightUnit("lbs")
                binding.radioButtonKilograms.id -> viewModel.setWeightUnit("kg")
            }
        }
        
        binding.sliderRestTimer.addOnChangeListener { _, value, _ ->
            viewModel.setDefaultRestTime(value.toInt())
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
