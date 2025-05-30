package com.example.dummyjsonapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductDetailsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Float,
    val discountPercentage: Float,
    val description: String,
    val rating: Float,
    val stock: Int,
    val image: String
)
