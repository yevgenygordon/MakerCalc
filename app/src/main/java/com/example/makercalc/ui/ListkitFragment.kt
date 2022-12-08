package com.example.makercalc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.adapter.Kundenadapter
import com.example.makercalc.adapter.Listkitadapter
import com.example.makercalc.databinding.FragmentListkitBinding

class ListkitFragment : Fragment() {

    private lateinit var binding: FragmentListkitBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListkitBinding.inflate(inflater)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.listenCalcRecycler)


        val adapter = Listkitadapter(viewModel)
        binding.listenCalcRecycler.adapter = adapter






    }


}