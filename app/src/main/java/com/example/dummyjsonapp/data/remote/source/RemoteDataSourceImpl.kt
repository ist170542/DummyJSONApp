package com.example.dummyjsonapp.data.remote.source

import com.example.dummyjsonapp.data.remote.ProductAPIService
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import retrofit2.HttpException

class RemoteDataSourceImpl(
    private val apiService: ProductAPIService
) : RemoteDataSource {
    override suspend fun getProducts(): List<ProductDTO> {

        val response = apiService.getProducts()

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw HttpException(response)
        }

    }

}