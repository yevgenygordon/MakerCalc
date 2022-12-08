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
import com.example.makercalc.adapter.Deviceadapter
import com.example.makercalc.adapter.Materialadapter
import com.example.makercalc.data.model.Devices
import com.example.makercalc.data.model.Materials
import com.example.makercalc.databinding.FragmentDeviceBinding

class devicesFragment : Fragment() {

    private lateinit var binding: FragmentDeviceBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDeviceBinding.inflate(inflater)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceInputFeld = binding.deviceInputFeld

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.deviceRecyclerView)

        val devicesAdapter = Deviceadapter(viewModel)
        binding.deviceRecyclerView.adapter = devicesAdapter

        viewModel.getDevices()
        viewModel.devices.observe(
            viewLifecycleOwner
        ){
            devicesAdapter.submitdeviceList(it.toMutableList())
        }


        // Device Inputfeld
        binding.btnPlusDevices.setOnClickListener {
            deviceInputFeld.visibility = View.VISIBLE
        }


        // Device in LiveData hinzufügen
        binding.btnDeviceSave.setOnClickListener {

            val newSingleCalcDevice = Devices (
                deviceName = binding.deviceNameInputTI.text.toString(),
                deviceTime = binding.deviceTimetInputTI.text.toString().toInt(),  // in Min
                devicePower = binding.devicePowerInputTI.text.toString().toInt(),
                deviceAmortizationPrice = binding.devicePreisInputTI.text.toString().toDouble()
            )

            viewModel.setNewDevices(newSingleCalcDevice)
            viewModel.calc.value?.let { it1 -> viewModel.getCalc(it1.singleCalcID) }


            deviceInputFeld.visibility = View.GONE


        }



        binding.btnOKdevices.setOnClickListener {
            viewModel.getCalc(viewModel.calc.value!!.singleCalcID)
            findNavController().navigateUp()
        }



  viewModel.currentUser.observe(
      viewLifecycleOwner, Observer {
          if (it == null){
              findNavController().navigate(R.id.loginFragment)
                }
                else{
                    viewModel.getUserData()
                }
            }
        )




    }

}