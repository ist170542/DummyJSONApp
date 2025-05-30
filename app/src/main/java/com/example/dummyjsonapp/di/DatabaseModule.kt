package com.example.dummyjsonapp.di

import android.content.Context
import androidx.room.Room
import com.example.dummyjsonapp.data.local.database.AppDatabase
import com.example.dummyjsonapp.data.local.database.ProductDetailsDao
import com.example.dummyjsonapp.data.local.database.ProductsDao
import com.example.dummyjsonapp.data.local.source.LocalDataSource
import com.example.dummyjsonapp.data.local.source.LocalDataSourceImpl
import com.example.dummyjsonapp.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppConstants.APP_DATABASE_NAME
        ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Singleton
    @Provides
    fun provideProductsDao(database: AppDatabase) = database.productsDao()

    @Singleton
    @Provides
    fun provideProductDetailsDao(database: AppDatabase) = database.productDetailsDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        productsDao: ProductsDao,
        productDetailsDao: ProductDetailsDao
    ): LocalDataSource = LocalDataSourceImpl(productsDao, productDetailsDao)
}