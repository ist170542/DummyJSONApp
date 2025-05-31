package com.example.dummyjsonapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.local.entities.ProductEntity
import com.example.dummyjsonapp.data.local.entities.ProductFTSEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductDetailsEntity::class,
        ProductFTSEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun productDetailsDao(): ProductDetailsDao
}
