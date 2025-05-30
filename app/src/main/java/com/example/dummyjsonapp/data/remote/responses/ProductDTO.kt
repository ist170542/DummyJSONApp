package com.example.dummyjsonapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ProductDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("images")
    val images: List<String>,

    @SerializedName("price")
    val price: Int,

    @SerializedName("discountPercentage")
    val discountPercentage: Float,

    @SerializedName("stock")
    val stock: Int,
)
