package com.example.dummyjsonapp.domain.model

import com.example.dummyjsonapp.R

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val thumbnail: String,
) {

    fun getRatingIcon(): Int {
        return when {
            rating >= 4.0 -> R.drawable.ic_good_rating
            rating >= 2.5 -> R.drawable.ic_neutral_rating
            else -> R.drawable.ic_bad_rating
        }
    }
}

