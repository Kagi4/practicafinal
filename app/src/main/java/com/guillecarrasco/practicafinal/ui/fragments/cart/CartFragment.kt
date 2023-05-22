package com.guillecarrasco.practicafinal.ui.fragments.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.databinding.FragmentCartBinding
import com.guillecarrasco.practicafinal.models.Product
import com.guillecarrasco.practicafinal.ui.fragments.cart.adapters.CartAdapter

class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCartBinding.inflate(inflater).apply {
            database = Firebase.database.reference
            auth = Firebase.auth
            binding = this
            setAdapter()
            setListeners()
            retrieveCart()
        }.root
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = CartAdapter()
    }

    private fun setListeners() {
        with(binding) {
            buttonAddToCart.setOnClickListener {
                showCartAlert()
            }
        }
    }

    private fun showCartAlert() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("SuperStore")
        alertDialogBuilder.setMessage("Are you sure you want to buy all this items?")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            removeCart()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun removeCart() {
        auth.currentUser?.uid?.let {
            database.child(it).child("cart").removeValue()
        }
    }

    private fun retrieveCart() {
        auth.currentUser?.uid?.let {
            database.child(it).child("cart").ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = snapshot.children.map { snap -> snap.getValue(Product::class.java) }
                    (binding.recyclerView.adapter as CartAdapter).setItems(products.requireNoNulls())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error on DB ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}