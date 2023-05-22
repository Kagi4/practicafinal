package com.guillecarrasco.practicafinal.ui.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.databinding.FragmentDetailsBinding
import com.guillecarrasco.practicafinal.models.Product

class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater).apply {
            binding = this
            database = Firebase.database.reference
            auth = Firebase.auth
            (this@DetailsFragment.arguments?.getParcelable(PRODUCT_KEY) as? Product)?.let { product ->
                bindView(product)
            }

        }.root
    }

    private fun bindView(product: Product) {
        with(binding) {
            textViewBrand.text = product.brand
            textViewDescription.text = product.description
            textViewPrice.text = "${product.price}$"
            textViewRating.text = "Rating: ${product.rating}"
            textViewTitle.text = product.title
            Glide.with(requireContext())
                .load(product.thumbnail)
                .into(imageView)
            buttonAddToCart.setOnClickListener {
                auth.currentUser?.uid?.let {
                    val childRef = database.child(it).child("cart").push()
                    childRef.setValue(product)
                }
            }
        }
    }

    companion object {
        const val PRODUCT_KEY = "PRODUCT_KEY"
    }
}