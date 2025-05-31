package com.example.dummyjsonapp.domain.useCases

import com.example.dummyjsonapp.data.local.datastore.SettingsDataStore
import com.example.dummyjsonapp.domain.repositories.ProductsRepository
import com.example.dummyjsonapp.util.ErrorType
import com.example.dummyjsonapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InitializeApplicationDataUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val settingsDataStore: SettingsDataStore
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        if (!settingsDataStore.isDataRetrieved()) {

            productsRepository.fetchAndCacheProductData().collect { resource ->
                emit(resource)
            }

        } else {
            emit(Resource.Success(Unit))
        }

    }.catch {
        emit(Resource.Error(ErrorType.UnknownError))
    }
}