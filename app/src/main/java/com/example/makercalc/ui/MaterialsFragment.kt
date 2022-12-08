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
import com.example.makercalc.adapter.Materialadapter
import com.example.makercalc.data.model.Materials
import com.example.makercalc.databinding.FragmentMaterialBinding

class MaterialsFragment : Fragment() {

    private lateinit var binding: FragmentMaterialBinding
    //   var personList: MutableList<Person> = mutableListOf(Person(1,"Alex","2222"))
    // private lateinit var calcList:MutableList<SingleCalc>


    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMaterialBinding.inflate(inflater)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val materialInputField = binding.materialInputFeld

        /*

           viewModel.currentUser.observe(
               viewLifecycleOwner, Observer {
                   if (it == null) {
                       findNavController().navigate(R.id.loginFragment)
                   } else {
                       //   viewModel.updateIsTemplate()
                       viewModel.getUserData()
                       viewModel.getMaterials()
                   }
               }
           )

         */


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.materialRecyclerView)

        val materialAdapter = Materialadapter(viewModel)
        binding.materialRecyclerView.adapter = materialAdapter


        viewModel.getMaterials()

        viewModel.materials.observe(
            viewLifecycleOwner
        ) {
            materialAdapter.submitMaterialsList(it.toMutableList())
        }








        binding.btnPlusMaterials.setOnClickListener {
            materialInputField.visibility = View.VISIBLE

        }

        binding.btnMaterialSave.setOnClickListener {


            val newSingleCalMaterial = Materials(
                materialName = binding.materialNameInputTI.text.toString(),
                materialPriceItem = binding.materialPreisInputTI.text.toString().toDouble(),
                materialweight = binding.materialWeightInputTI.text.toString().toDouble(),
                materialQuantity = binding.materialQuantityInputTI.text.toString().toInt()
            )



            viewModel.setNewMaterial(newSingleCalMaterial)
            viewModel.calc.value?.let { it1 -> viewModel.getCalc(it1.singleCalcID) }
           // viewModel.getMaterials()


            materialInputField.visibility = View.GONE


        }


        binding.btnOKmaterials.setOnClickListener {
            viewModel.getCalc(viewModel.calc.value!!.singleCalcID)
            findNavController().navigateUp()
        }


    }


}