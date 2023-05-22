package com.guillecarrasco.practicafinal.api

import com.guillecarrasco.practicafinal.models.Products
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<Products>
}