package com.example.dummyjsonapp.data.remote.source

import com.example.dummyjsonapp.data.remote.responses.ProductDTO

interface RemoteDataSource {
    suspend fun getProducts(): List<ProductDTO>
}