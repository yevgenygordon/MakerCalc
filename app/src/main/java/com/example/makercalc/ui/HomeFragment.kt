package com.example.makercalc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.adapter.HomeCalcadapter
import com.example.makercalc.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    private val viewModel: MainViewModel by activityViewModels()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // viewModel.getCalcs()


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)





        var calcAdapter = HomeCalcadapter(viewModel)
        binding.recyclerView.adapter = calcAdapter


        viewModel.calcs.observe(
            viewLifecycleOwner
        ){
            calcAdapter.submitCalcList(it.toMutableList())
        }

/*
        viewModel.calc.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.getCalcs()
            }
        )
*/

        // Btn Logout
        binding.btnLogout.setOnClickListener { viewModel.logout() }


       // Btn Neuer Calc
        binding.btnPlus.setOnClickListener {
            findNavController().navigate(R.id.constructionkitselectionFragment)
        }


       // Btn LOGO
        binding.logoIV2.setOnClickListener {

        }










        viewModel.currentUser.observe(

            viewLifecycleOwner, Observer {

                if (it == null){

                    findNavController().navigate(R.id.loginFragment)
                }
                else{

                    viewModel.getUserData()

                    if (viewModel.sortBy.value?.filterFavorite == true) {
                        viewModel.filterFavorite()
                    }
                    if (viewModel.sortBy.value?.filterName == true) {
                        viewModel.sortByName()
                    }

                    else {
                        viewModel.getCalcs()
                    }

                    viewModel.image = ""


                }
            }
        )




    }







}