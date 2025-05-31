package com.example.dummyjsonapp.data.local.source

import androidx.paging.PagingSource
import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.local.entities.ProductEntity

interface LocalDataSource {

    // Products
    fun searchProducts(query: String): PagingSource<Int, ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)
    fun getAllProductsPaged(): PagingSource<Int, ProductEntity>

    // Product Details
    suspend fun getProductDetails(id: Int): ProductDetailsEntity?
    suspend fun insertProductDetails(productDetails: List<ProductDetailsEntity>)
}