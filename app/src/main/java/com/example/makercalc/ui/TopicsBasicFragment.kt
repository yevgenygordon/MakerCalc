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
import com.example.makercalc.databinding.FragmentTopicsBinding
import com.example.makercalc.databinding.FragmentTopicsbasicBinding

class TopicsBasicFragment : Fragment() {

    private lateinit var binding: FragmentTopicsbasicBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopicsbasicBinding.inflate(inflater)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.themenString33TV3.setOnClickListener {
         //   viewModel.getTemplateCalc()
         //   findNavController().navigate(TopicsBasicFragmentDirections.actionTopicsBasicFragmentToConstructionkitFragment())

        }


    }
}