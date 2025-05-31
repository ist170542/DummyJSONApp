package com.example.dummyjsonapp.data.remote.source

import com.example.dummyjsonapp.data.remote.ProductAPIService
import com.example.dummyjsonapp.data.remote.responses.PaginatedGetProductsResponseDto
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
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

    override suspend fun getProductsPaginated(
        skip: Int,
        limit: Int
    ): PaginatedGetProductsResponseDto {
        val response = apiService.getProductsPaginated(skip = skip, limit)

        if (response.isSuccessful) {
            return response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

}