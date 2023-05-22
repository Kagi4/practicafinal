package com.guillecarrasco.practicafinal.ui.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.R
import com.guillecarrasco.practicafinal.api.ApiService
import com.guillecarrasco.practicafinal.databinding.FragmentPoductsBinding
import com.guillecarrasco.practicafinal.models.Product
import com.guillecarrasco.practicafinal.ui.fragments.product.adapters.ProductAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsFragment: Fragment() {

    private lateinit var binding: FragmentPoductsBinding
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPoductsBinding.inflate(inflater).apply {
            binding = this
            navController = findNavController()
            database = Firebase.database.reference
            auth = Firebase.auth

            retrieveProducts()
        }.root
    }

    private fun retrieveProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        lifecycleScope.launch {
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    val products = response.body()
                    if (products != null) {
                        setAdapter(products.products)
                    }
                } else {
                    Toast.makeText(requireContext(), "Unable to retrieve products", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unable to connect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapter(products: List<Product>) {
        val adapter = ProductAdapter(object : ProductAdapter.OnClickedItemListener {
            override fun onItemBuy(item: Product) {
                auth.currentUser?.uid?.let {
                    val childRef = database.child(it).child("cart").push()
                    childRef.setValue(item)
                }
            }

            override fun onItemDetails(item: Product) {
                navController.navigate(R.id.action_productsFragment_to_detailsFragment, Bundle().apply {
                    putParcelable(DetailsFragment.PRODUCT_KEY ,item)
                })
            }
        })
        adapter.setItems(products)
        binding.rvProducts.adapter = adapter
    }
}