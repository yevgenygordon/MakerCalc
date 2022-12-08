package com.example.makercalc.ui.authentication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.User
import com.example.makercalc.databinding.FragmentSignupBinding

class SignupFragment: Fragment(){

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSignup.setOnClickListener {
            signUp()
        }

        viewModel.toast.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(requireContext())
                        .setMessage(it)
                        .create()
                        .show()
                }
            }
        )

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        )
    }

    private fun signUp() {
        val email = binding.signupLoginNameTI.text.toString()
        val password = binding.signupPasswordTI.text.toString()



        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            val newUser = User(
                userID = "",
                userName = "",
                userEmail = email,
                userPassword = password,
                userImage = "",
                userAdress = "",
                userCity = "",
                userPostcode = "",
                userTelNumber = "",
                userCalcs = mutableListOf(),
         //       userKunden = listOf(),

            )
            viewModel.signUp(email, password, newUser)
        }
    }

}