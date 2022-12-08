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
import com.example.makercalc.adapter.Kundenadapter
import com.example.makercalc.adapter.Topicadapter
import com.example.makercalc.data.model.Kunden
import com.example.makercalc.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var binding: FragmentTopicsBinding

    private val topicViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopicsBinding.inflate(inflater)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.topicRecyclerView)




        val adapter = Topicadapter(topicViewModel)
        binding.topicRecyclerView.adapter = adapter



        binding.btnOKtopics.setOnClickListener {

         //   findNavController().navigate(TopicsBasicFragmentDirections.actionTopicsBasicFragmentToConstructionkitFragment())
          //  findNavController().navigate(TopicsFragmentDirections.actionTopicsFragmentToConstructionkitFragment())

            findNavController().navigateUp()

        }

        topicViewModel.themenliste.observe(
            viewLifecycleOwner, Observer {

                adapter.submitTopicList(it)
            }
        )


        topicViewModel.currentUser.observe(

            viewLifecycleOwner, Observer {

                if (it == null){

                    findNavController().navigate(R.id.loginFragment)
                }
                else{

                    topicViewModel.getUserData()
                    topicViewModel.getTheme()
                    topicViewModel.setTemp()

                }
            }
        )





        }




    }
