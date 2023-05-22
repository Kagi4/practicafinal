package com.guillecarrasco.practicafinal.ui.fragments.login

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
import com.guillecarrasco.practicafinal.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLoginBinding.inflate(inflater).apply {
            binding = this
            navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            auth = Firebase.auth
            setListeners()
        }.root
    }

    private fun setListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPass.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            navController.navigate(R.id.action_loginFragment_to_productsActivity)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
            buttonRegister.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}