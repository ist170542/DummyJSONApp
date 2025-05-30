package com.example.dummyjsonapp.di

import com.example.dummyjsonapp.data.remote.ProductAPIService
import com.example.dummyjsonapp.data.remote.source.RemoteDataSource
import com.example.dummyjsonapp.data.remote.source.RemoteDataSourceImpl
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Hilt Module to provide API and Remote data source
 */
object NetworkModule {

    @Singleton
    @Provides
    fun provideProductAPIService(retrofit: Retrofit): ProductAPIService =
        retrofit.create(ProductAPIService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ProductAPIService): RemoteDataSource =
        RemoteDataSourceImpl(apiService)

}