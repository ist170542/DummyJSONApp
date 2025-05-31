package com.example.dummyjsonapp.data.local.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = ProductEntity::class)
@Entity(tableName = "products_fts")
data class ProductFTSEntity (
    val title: String,
    val description: String
)