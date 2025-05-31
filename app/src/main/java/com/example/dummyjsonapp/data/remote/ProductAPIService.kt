package com.example.dummyjsonapp.data.remote

import com.example.dummyjsonapp.data.remote.responses.PaginatedGetProductsResponseDto
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPIService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductDTO>>

    @GET("products")
    suspend fun getProductsPaginated(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<PaginatedGetProductsResponseDto>

}