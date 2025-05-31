package com.example.dummyjsonapp.di

import com.example.dummyjsonapp.data.remote.ProductAPIService
import com.example.dummyjsonapp.data.remote.source.RemoteDataSource
import com.example.dummyjsonapp.data.remote.source.RemoteDataSourceImpl
import com.example.dummyjsonapp.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt Module to provide API and Remote data source
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideProductAPIService(retrofit: Retrofit): ProductAPIService =
        retrofit.create(ProductAPIService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ProductAPIService): RemoteDataSource =
        RemoteDataSourceImpl(apiService)

}