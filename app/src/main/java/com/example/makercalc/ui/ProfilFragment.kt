package com.example.makercalc.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    private val viewModel: MainViewModel by activityViewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadImage(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfilBinding.inflate(inflater)


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(

            viewLifecycleOwner, Observer {

                if (it == null){

                    findNavController().navigate(R.id.loginFragment)
                }
                else{

                    viewModel.getUserData()
                    viewModel.getCalcs()

                }
            }
        )




        viewModel.user.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {



                    binding.profilNameTV.setText( it.userName)
                    binding.profilAdressTV.setText(it.userAdressText)
                    binding.profilFooterTV.setText(it.userFooter)
                    binding.profilFotoIV.load(it.userImage) {
                        error(resources.getDrawable(R.drawable.no_image))



                    }
                }
            }
        )



        binding.btnInsertProfileImage.setOnClickListener {
            getContent.launch("image/*")

            viewModel.getUserData()
        }



        binding.btnOKprofile.setOnClickListener {



            viewModel.user.value?.userName  = binding.profilNameTV.text.toString()
            viewModel.user.value?.userAdressText = binding.profilAdressTV.text.toString()
            viewModel.user.value?.userFooter = binding.profilFooterTV.text.toString()


            viewModel.updateUser()

            findNavController().navigate(R.id.homeFragment)
        }

    }
}