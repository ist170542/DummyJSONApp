package com.example.dummyjsonapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PaginatedGetProductsResponseDto (
    @SerializedName("products")
    val products: List<ProductDTO>,

    @SerializedName("total")
    val total: Int,

    @SerializedName("skip")
    val skip: Int,

    @SerializedName("limit")
    val limit: Int

)