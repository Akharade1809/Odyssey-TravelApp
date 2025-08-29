package com.example.odyssey.data.local.extentions

import com.example.odyssey.data.local.entity.DestinationEntity
import com.example.odyssey.data.models.Destination

fun DestinationEntity.toDomain() : Destination {
    return Destination(
        id = id,
        name = name,
        country = country,
        description = description,
        imageUrl = imageUrl,
        rating = rating,
        price = price,
        currency = currency,
        category = category,
        isPopular = isPopular,
        isFavorite = isFavorite,
    )
}

fun Destination.toEntity(): DestinationEntity {
    return DestinationEntity(
        id = id,
        name = name,
        country = country,
        description = description,
        imageUrl = imageUrl,
        rating = rating,
        price = price,
        currency = currency,
        category = category,
        isPopular = isPopular,
        isFavorite = isFavorite
    )
}