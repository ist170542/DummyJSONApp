package com.example.dummyjsonapp.domain.model

/**
 * Product details domain model class. Created to reduce the burden of searching/filtering the
 * product list. A lot of the info is only used in the Product Details screen
 */
data class ProductDetails(
    val id: Int,
    val title: String,
    val price: Float,
    val discountPercentage: Float,
    val description: String,
    val rating: Float,
    val stock: Int,
    val image: String?,
    val tags: List<String> = emptyList()
)