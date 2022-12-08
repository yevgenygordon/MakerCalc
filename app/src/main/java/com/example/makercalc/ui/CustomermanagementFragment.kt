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
import com.example.makercalc.data.model.Kunden
import com.example.makercalc.databinding.FragmentCustumermanagementBinding

class CustomermanagementFragment : Fragment() {

    private lateinit var binding: FragmentCustumermanagementBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustumermanagementBinding.inflate(inflater)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.kundenReyclerView)


        val adapter = Kundenadapter(viewModel)
        binding.kundenReyclerView.adapter = adapter


        binding.btnOKcustomer.setOnClickListener {

            findNavController().navigate(R.id.homeFragment)
        }



        val kundenName = binding.kundenNameInputTI
        val kundenAddress = binding.kundenAdresseInputTI
        val kundenPostcode = binding.kundenPostcodeInputTI
        val kundenCity = binding.kundenStadtInputTI
        val kundenInputFeld = binding.kundenInputFeld






        binding.btnPlusCustomer.setOnClickListener {

           kundenInputFeld.visibility = View.VISIBLE
 }


        fun setKunde() {


            if (!kundenName.text.isNullOrEmpty()) {
                val newKunde = Kunden(


                    userID = "",
                    kundenName = kundenName.text.toString(),
                    kundenEmail = "",
                    kundenImage = "",

                    kundenAdress = kundenAddress.text.toString(),
                    kundenCity = kundenCity.text.toString(),
                    kundenPostcode = kundenPostcode.text.toString(),
                    kundenTelNumber = "",

                    )
                viewModel.updateKunde(newKunde)
            }


        }





        viewModel.getKunden()


        viewModel.kunden.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )



         binding.btnKundenSave.setOnClickListener {

             setKunde()


             kundenInputFeld.visibility = View.GONE

             viewModel.getKunden()

         }



    }
}