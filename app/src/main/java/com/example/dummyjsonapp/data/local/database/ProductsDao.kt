package com.example.dummyjsonapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dummyjsonapp.data.local.entities.ProductEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

}