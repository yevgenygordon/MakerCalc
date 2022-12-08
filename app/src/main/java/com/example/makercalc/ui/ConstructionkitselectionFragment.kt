package com.example.makercalc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.databinding.FragmentConstructionkitselectionBinding

class ConstructionkitselectionFragment : Fragment() {

    private lateinit var binding: FragmentConstructionkitselectionBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConstructionkitselectionBinding.inflate(inflater)


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fromTemplateIV.setOnClickListener {
            viewModel.getTheme()
            viewModel.setTemp()
            findNavController().navigate(R.id.topicsFragment)

        }
        binding.fromTemplateTV.setOnClickListener {
            viewModel.getTheme()
            viewModel.setTemp()
            findNavController().navigate(R.id.topicsFragment) }


    }


}