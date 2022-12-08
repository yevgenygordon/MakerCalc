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
import com.example.makercalc.databinding.FragmentSortbyBinding

class SortbyFragment : Fragment() {

    private lateinit var binding: FragmentSortbyBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSortbyBinding.inflate(inflater)


        return binding.root


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        binding.btnOKSortby.setOnClickListener {

         //   viewModel.sortBy.value?.filterFavorite = binding.switchFavorit.isChecked
          viewModel.sortBy.value?.filterName = binding.switchFavorit.isChecked

            findNavController().navigate(R.id.homeFragment)
        }

    }
}