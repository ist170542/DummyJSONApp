package com.example.dummyjsonapp.di

import com.example.dummyjsonapp.data.local.datastore.SettingsDataStore
import com.example.dummyjsonapp.data.local.source.LocalDataSource
import com.example.dummyjsonapp.data.remote.source.RemoteDataSource
import com.example.dummyjsonapp.data.repositories.ProductsRepositoryImpl
import com.example.dummyjsonapp.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProductsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        settingsDataStore: SettingsDataStore
    ) : ProductsRepository {
        return ProductsRepositoryImpl(localDataSource, remoteDataSource, settingsDataStore)
    }
}