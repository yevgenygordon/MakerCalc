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
import com.example.makercalc.adapter.HomeCalcadapter
import com.example.makercalc.databinding.FragmentHomeBinding
import com.example.makercalc.databinding.FragmentMenuBinding

class MenuFragment: Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(inflater)


        return binding.root


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



     binding.profileditTV.setOnClickListener { findNavController().navigate(R.id.profilFragment) }
     binding.profilIconIV.setOnClickListener { findNavController().navigate(R.id.profilFragment) }

     binding.customerEditTV.setOnClickListener { findNavController().navigate(R.id.customermanagementFragment) }
     binding.customerIV.setOnClickListener { findNavController().navigate(R.id.customermanagementFragment) }



        binding.btnOK.setOnClickListener {

            findNavController().navigate(R.id.homeFragment)
        }

    }


}