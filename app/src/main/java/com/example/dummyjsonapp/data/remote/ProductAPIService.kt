package com.example.dummyjsonapp.data.remote

import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPIService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductDTO>>

}