package com.example.dummyjsonapp.data.local.source

import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.local.entities.ProductEntity

interface LocalDataSource {

    // Products
    suspend fun getProducts(): List<ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)

    // Product Details
    suspend fun getProductDetails(id: Int): ProductDetailsEntity
    suspend fun insertProductDetails(productDetails: List<ProductDetailsEntity>)

}