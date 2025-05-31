package com.example.dummyjsonapp.domain.repositories

import androidx.paging.PagingData
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.domain.model.ProductDetails
import com.example.dummyjsonapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun fetchAndCacheProductData(): Flow<Resource<Unit>>
    fun getProductDetails(id: Int): Flow<Resource<ProductDetails>>
    fun searchProducts(query: String): Flow<Resource<PagingData<Product>>>
}