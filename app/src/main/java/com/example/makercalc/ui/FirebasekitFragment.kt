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
import com.example.makercalc.data.model.SingleCalc
import com.example.makercalc.databinding.FragmentFirebasekitBinding


class FirebasekitFragment : Fragment() {

    private lateinit var binding: FragmentFirebasekitBinding

    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirebasekitBinding.inflate(inflater)

         return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



      //  viewModel.tempcalc.value?.let { println(it.electricityPrice) }
      //  viewModel.user.value?.let { println(it.userEmail) }



        viewModel.tempcalc.observe(
            viewLifecycleOwner,
            {
                binding.calcNameET.setText(it.singleCalcName.toString())
                binding.calcTextTI.setText(it.singleCalcDescription.toString())
                binding.produktDifficultyTI.setText(it.difficultyFactor.toString())
                binding.produktQuantityTI.setText(it.quantity.toString())
                binding.workTimeTI.setText(it.workTime.toString())
                binding.workPreisTI.setText(it.workExtraPrice.toString())
                binding.extraPreisTI.setText(it.workExtraPrice.toString())
                binding.postPrPreisTI.setText(it.postProcessingPrice.toString())
                binding.moddelingCostTI.setText(it.modelingCost.toString())
                binding.electricityPreisTI.setText(it.electricityPrice.toString())

            }
        )





        fun setNewCalc () {

            val newSingleCalc = SingleCalc(
             singleCalcID = ""   ,
             singleCalcName = binding.calcNameET.text.toString(),
             singleCalcDescription = binding.calcTextTI.text.toString(),
             prototype = 0,
             difficultyFactor = binding.produktDifficultyTI.text.toString().toDouble(),
             quantity = binding.produktQuantityTI.text.toString().toInt(),

            workTime = binding.workTimeTI.text.toString().toDouble(),  // Std
            workPriceHour = binding.workPreisTI.text.toString().toDouble(),
            workExtraPrice = binding.extraPreisTI.text.toString().toDouble(),
            postProcessingPrice = binding.postPrPreisTI.text.toString().toDouble(),
            modelingCost = binding.moddelingCostTI.text.toString().toDouble(),
            electricityPrice = binding.electricityPreisTI.text.toString().toDouble(),  //($/1000 Watt)
            isTemplate = 0,
                isFavorite = 0,
            themeID = "",
                )

         //   viewModel.setTemplateCalc(newSingleCalc)

        }






        binding.btnPlusMaterial.setOnClickListener {
            findNavController().navigate(R.id.materialsFragment)
        }


        binding.btnPlusDevice.setOnClickListener {
            findNavController().navigate(R.id.devicesFragment)
        }







        binding.btnFirebaseTemplateSaveCalc.setOnClickListener {

            setNewCalc()
            findNavController().navigate(R.id.homeFragment)
           // viewModel.getCalcs()
        }


    }


}