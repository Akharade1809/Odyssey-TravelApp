package com.example.odyssey.data.models

data class Destination(
    val id: String,
    val name: String,
    val country: String,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val price: Double,
    val currency: String,
    val category: String,
    val isPopular: Boolean = false,
    val isFavorite: Boolean = false
)
