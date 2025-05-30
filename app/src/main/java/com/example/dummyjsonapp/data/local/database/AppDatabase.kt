package com.example.dummyjsonapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, ProductDetailsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun productDetailsDao(): ProductDetailsDao
}
