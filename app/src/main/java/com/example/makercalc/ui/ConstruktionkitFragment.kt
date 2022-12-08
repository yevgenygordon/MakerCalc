package com.example.makercalc.ui


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.SingleCalc
import com.example.makercalc.databinding.FragmentConstructionkitBinding


class ConstructionkitFragment : Fragment() {

    private lateinit var binding: FragmentConstructionkitBinding

    private val viewModel: MainViewModel by activityViewModels()


    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadCalcImage(uri)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConstructionkitBinding.inflate(inflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val theme = requireArguments().getString("theme", "")


        viewModel.calc.observe(
            viewLifecycleOwner,
            Observer {

                binding.calcNameET.setText(it.singleCalcName)
                binding.calcTextTI.setText(it.singleCalcDescription)
                binding.produktDifficultyTI.setText(it.difficultyFactor.toString())
                binding.produktQuantityTI.setText(it.quantity.toString())
                binding.workTimeTI.setText(it.workTime.toString())
                binding.workPreisTI.setText(it.workPriceHour.toString())
                binding.extraPreisTI.setText(it.workExtraPrice.toString())
                binding.postPrPreisTI.setText(it.postProcessingPrice.toString())
                binding.moddelingCostTI.setText(it.modelingCost.toString())
                binding.electricityPreisTI.setText(it.electricityPrice.toString())

                binding.calcImageEI.load(it.image) {
                    error(resources.getDrawable(R.drawable.no_image))
                }


            }
        )




        fun updateCalcFuntion(): SingleCalc {

            var updateCalc = SingleCalc(
                singleCalcID = viewModel.calc.value!!.singleCalcID,
                singleCalcName = binding.calcNameET.text.toString(),
                singleCalcDescription = binding.calcTextTI.text.toString(),
                themeID = viewModel.calc.value!!.themeID,
                image = viewModel.calc.value!!.image,
                isTemplate = 0,
                isFavorite = 0,
                prototype = if (binding.prototypeSwitchCalckit.isChecked) 1 else 0,
                difficultyFactor = binding.produktDifficultyTI.text.toString().toDouble(),
                quantity = binding.produktQuantityTI.text.toString().toInt(),
                workTime = binding.workTimeTI.text.toString().toDouble(),
                workPriceHour = binding.workPreisTI.text.toString().toDouble(),
                workExtraPrice = binding.extraPreisTI.text.toString().toDouble(),
                postProcessingPrice = binding.postPrPreisTI.text.toString().toDouble(),
                modelingCost = binding.moddelingCostTI.text.toString().toDouble(),
                electricityPrice = binding.electricityPreisTI.text.toString().toDouble(),


                )

            return updateCalc
        }


        // Btn insert Material Fragment
        binding.btnPlusMaterial.setOnClickListener {


            viewModel.updateSingleCalc(updateCalcFuntion())
            viewModel.getCalc(updateCalcFuntion().singleCalcID)


/*
                    if (viewModel.calc.value!!.isTemplate == 1) {
                        viewModel.getTemplateMaterial()
                        viewModel.getTemplateDevice()
                        viewModel.calc.value!!.isTemplate = 0

                    }

            viewModel.getCalc(updateCalc.singleCalcID)

 */
            findNavController().navigate(R.id.materialsFragment)
        }


        // Btn insert device Fragment
        binding.btnPlusDevice.setOnClickListener {

            viewModel.updateSingleCalc(updateCalcFuntion())
            viewModel.getCalc(updateCalcFuntion().singleCalcID)
            findNavController().navigate(R.id.devicesFragment)
        }


        // Btn insert CalcImage
        binding.btnInsertCalcImage.setOnClickListener {

            viewModel.updateSingleCalc(updateCalcFuntion())
            viewModel.getCalc(updateCalcFuntion().singleCalcID)
            getContent.launch("image/*")
        }


        //Btn Construktionkit beenden
        val queryField = binding.queryField
        binding.btnSaveCalc.setOnClickListener {
            queryField.visibility = View.VISIBLE
        }


        //Btn Construktionkit weiter ausfüllen
        binding.btnQueryCancel.setOnClickListener {

            queryField.visibility = View.GONE
        }

        //Btn verwerfen
        binding.btnQueryDiscard.setOnClickListener {

            val delCalc = viewModel.calc.value!!.singleCalcID
            viewModel.deleteCalc(delCalc)
        }


        //Btn Save Calc to Firebase
        binding.btnQuerySave.setOnClickListener {


            viewModel.updateSingleCalc(updateCalcFuntion())

            viewModel.image = ""

            findNavController().navigate(R.id.homeFragment)

        }


    }


}