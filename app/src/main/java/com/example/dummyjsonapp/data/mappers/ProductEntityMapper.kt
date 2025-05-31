package com.example.dummyjsonapp.data.mappers

import com.example.dummyjsonapp.data.local.entities.ProductEntity
import com.example.dummyjsonapp.data.remote.responses.ProductDTO
import com.example.dummyjsonapp.domain.model.Product

object ProductEntityMapper {
    fun fromEntity(entity: ProductEntity): Product {
        return Product(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            rating = entity.rating,
            thumbnail = entity.thumbnail
        )
    }

    fun fromDto(dto: ProductDTO): ProductEntity {
        return ProductEntity(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            rating = dto.rating,
            thumbnail = dto.thumbnail
        )
    }

    fun toDomain(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            title = productEntity.title,
            description = productEntity.description,
            rating = productEntity.rating,
            thumbnail = productEntity.thumbnail
        )
    }

}