package com.example.dummyjsonapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.dummyjsonapp.data.local.datastore.SettingsDataStore
import com.example.dummyjsonapp.data.local.source.LocalDataSource
import com.example.dummyjsonapp.data.mappers.ProductDetailsEntityMapper
import com.example.dummyjsonapp.data.mappers.ProductEntityMapper
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import com.example.dummyjsonapp.data.remote.source.RemoteDataSource
import com.example.dummyjsonapp.domain.model.Product
import com.example.dummyjsonapp.domain.model.ProductDetails
import com.example.dummyjsonapp.domain.repositories.ProductsRepository
import com.example.dummyjsonapp.util.ErrorType
import com.example.dummyjsonapp.util.Resource
import com.example.dummyjsonapp.util.TextNormalizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val settingsDataStore: SettingsDataStore
) : ProductsRepository {

    /**
     * Fetches products from the remote data source and caches them in the local data source.
     */
    override fun fetchAndCacheProductData(): Flow<Resource<Unit>> = flow {

        try {

            val productsDTO = mutableListOf<ProductDTO>()

            var skip = 0
            val limit = 30
            var total = Int.MAX_VALUE

            while (skip < total) {
                val productsDto = remoteDataSource.getProductsPaginated(skip, limit)
                productsDTO.addAll(productsDto.products)
                skip += limit
                total = productsDto.total
            }

            val productEntities = productsDTO.map { productDto ->
                ProductEntityMapper.fromDto(productDto)
            }

            val productDetailsEntities = productsDTO.map { productDto ->
                ProductDetailsEntityMapper.fromDto(productDto)
            }

            withContext(Dispatchers.IO) {
                localDataSource.insertProducts(productEntities)
                localDataSource.insertProductDetails(productDetailsEntities)
                settingsDataStore.setDataRetrieved(true)
            }

            emit(Resource.Success(Unit))

        } catch (httpException: HttpException) {
            emit(Resource.Error(ErrorType.ServerError))
        } catch (ioException: IOException) {
            emit(Resource.Error(ErrorType.NetworkError))
        } catch (exception: Exception) {
            emit(Resource.Error(ErrorType.UnknownError))
        }

    }

    /**
     * Returns paged product results based on a search query.
     */
    override fun searchProducts(query: String): Flow<Resource<PagingData<Product>>> = flow {

        try {
            // Normalize query to remove diacritics and convert to lowercase
            val trimmedQuery = TextNormalizer.normalizeText(query)
                .split("\\s+".toRegex())
                .joinToString(" ")
                { "$it*" } // add * to allow partial searching E.g. "Essenc" matches "Essence"


            // Check if the query text is empty after normalization, or if it only contains "*"
            val pagingSourceFactory = if (trimmedQuery.isBlank() || trimmedQuery == "*") {
                { localDataSource.getAllProductsPaged() }
            } else {
                { localDataSource.searchProducts("%$trimmedQuery%") }
            }

            val paged = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.map { productEntity ->
                    ProductEntityMapper.toDomain(productEntity)
                }
            }

            paged.collect{
                emit(Resource.Success(it))
            }

        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.UnknownError))
        }

    }

    /**
     * Returns product details based on a product ID.
     */
    override fun getProductDetails(id: Int): Flow<Resource<ProductDetails>> = flow {

        val productDetails = withContext(Dispatchers.IO) {
            localDataSource.getProductDetails(id)
        }

        if (productDetails != null) {
            emit(Resource.Success(ProductDetailsEntityMapper.fromEntity(productDetails)))
        } else {
            emit(Resource.Error(ErrorType.MissingProductId))
        }

    }

}