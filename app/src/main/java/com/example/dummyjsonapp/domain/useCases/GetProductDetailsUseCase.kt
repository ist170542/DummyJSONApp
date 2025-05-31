package com.example.dummyjsonapp.domain.useCases

import com.example.dummyjsonapp.domain.model.ProductDetails
import com.example.dummyjsonapp.domain.repositories.ProductsRepository
import com.example.dummyjsonapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(id: Int): Flow<Resource<ProductDetails>> {
        return productsRepository.getProductDetails(id)
    }
}