package com.example.dummyjsonapp.data.local.source

import androidx.paging.PagingSource
import com.example.dummyjsonapp.data.local.database.ProductDetailsDao
import com.example.dummyjsonapp.data.local.database.ProductsDao
import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.local.entities.ProductEntity

class LocalDataSourceImpl(
    private val productsDao: ProductsDao,
    private val productDetailsDao: ProductDetailsDao
) : LocalDataSource {

    override fun searchProducts(query: String): PagingSource<Int, ProductEntity> {
        return productsDao.searchProducts(query)
    }

    override fun getAllProductsPaged(): PagingSource<Int, ProductEntity> {
        return productsDao.getAllProductsPaged()
    }

    override suspend fun insertProducts(products: List<ProductEntity>) {
        productsDao.insertProducts(products)
    }

    override suspend fun getProductDetails(id: Int): ProductDetailsEntity {
        return productDetailsDao.getProductDetails(id)
    }

    override suspend fun insertProductDetails(productDetails: List<ProductDetailsEntity>) {
        productDetailsDao.insertProductsDetails(productDetails)
    }

}