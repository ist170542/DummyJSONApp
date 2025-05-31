package com.example.dummyjsonapp.data.mappers

import com.example.dummyjsonapp.data.local.entities.ProductDetailsEntity
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import com.example.dummyjsonapp.domain.model.ProductDetails

object ProductDetailsEntityMapper {
    fun fromEntity(entity: ProductDetailsEntity): ProductDetails {
        return ProductDetails(
            id = entity.id,
            title = entity.title,
            price = entity.price,
            discountPercentage = entity.discountPercentage,
            description = entity.description,
            rating = entity.rating,
            stock = entity.stock,
            image = entity.image,
            tags = entity.tags.split(",")
        )
    }

    fun fromDto(productDto: ProductDTO): ProductDetailsEntity {
        return ProductDetailsEntity(
            id = productDto.id,
            title = productDto.title,
            price = productDto.price,
            discountPercentage = productDto.discountPercentage,
            description = productDto.description,
            rating = productDto.rating,
            stock = productDto.stock,
            image = productDto.images.firstOrNull(),
            tags = productDto.tags.joinToString(",")
        )

    }
}