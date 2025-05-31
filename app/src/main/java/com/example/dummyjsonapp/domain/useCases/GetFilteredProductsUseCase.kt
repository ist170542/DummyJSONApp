package com.example.dummyjsonapp.domain.useCases

import androidx.paging.PagingData
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.domain.repositories.ProductsRepository
import com.example.dummyjsonapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
)
{
    operator fun invoke(query: String): Flow<Resource<PagingData<Product>>> = flow {
        emit(Resource.Loading)

        productsRepository.searchProducts(query).collect{
            emit(it)
        }
    }
}