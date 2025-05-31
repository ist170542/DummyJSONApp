package com.example.dummyjsonapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dummyjsonapp.data.local.entities.ProductEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products WHERE id IN (SELECT rowid FROM products_fts WHERE products_fts MATCH :query)")
    fun searchProducts(query: String): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products")
    fun getAllProductsPaged(): PagingSource<Int, ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

}