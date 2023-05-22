package com.guillecarrasco.practicafinal.ui.fragments.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.R
import com.guillecarrasco.practicafinal.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegisterBinding.inflate(inflater).apply {
            binding = this
            navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            auth = Firebase.auth
            setListeners()
        }.root
    }

    private fun setListeners() {
        with(binding) {

            dateEditText.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    dateEditText.setText(selectedDate)
                }, 1991, 1, 1)
                datePickerDialog.show()
            }
            buttonRegister.setOnClickListener {
                auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPass.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            navController.navigate(R.id.action_registerFragment_to_productsActivity)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
    }
}