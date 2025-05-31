package com.example.dummyjsonapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity

@Dao
interface ProductDetailsDao {

    @Query("SELECT * FROM productDetails WHERE id = :id")
    suspend fun getProductDetails(id: Int): ProductDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductsDetails(productDetails: List<ProductDetailsEntity>)

}